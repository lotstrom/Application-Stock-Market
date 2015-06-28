%% @Authors Ale Lotström
%% Module with all main functions: Fetching data from stock markets through URL's,
%% Decodes and encodes JSON, and uploads to database.

-module (parser).

-export ([start/4, init/4, gen_url/2]).

-define(SERVER, ?MODULE).

% Patternmatching the market and generate url with the first symbol in the list.
gen_url(Source, []) ->
	{Source, empty};
		
gen_url(google, [Symbol|Rest]) ->
	start("http://www.google.com/finance/info?infotype=infoquoteall&q="++ Symbol ++ "&callback=?", Symbol, Rest, google);

gen_url(yahoo, [Symbol|Rest]) ->
	start("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22" ++ Symbol ++ "%22)%0A%09%09&env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json", Symbol, Rest, yahoo);

gen_url(markitondemand, [Symbol|Rest]) ->
	start("http://dev.markitondemand.com/Api/Quote/jsonp?Symbol=" ++ Symbol, Symbol, Rest, markitondemand).

start(URL, Symbol, Rest, Source) ->
    try spawn(parser, init, [URL, Symbol, Rest, Source]) of
    	_Response -> ok
    catch
    	_:_ -> ok
    end.

init(URL, Symbol, Rest, google) ->	
	read_source(google,URL, Symbol),
	gen_url(google, Rest);

init(URL, Symbol, Rest, yahoo) ->	
	read_source(yahoo,URL, Symbol),
	gen_url(yahoo, Rest);

init(URL, Symbol, Rest, markitondemand) ->	
	read_source(markitondemand,URL, Symbol),
	gen_url(markitondemand, Rest).

%% Removing some unwanted characters in recieved data from the different sources.

read_source(google, URL, Symbol) ->
	inets:start(),
	case httpc:request(get, {URL,[]},[],[]) of
	{ok, {{_, 200, _}, _Headers, Body}} -> 
		inets:stop(),
		decode(google, Body, Symbol);
	_ -> error
end;

read_source(yahoo, URL, Symbol) ->  
	inets:start(),
	case httpc:request(get, {URL,[]},[],[]) of
	{ok, {{_, 200, _}, _Headers, Body}} -> 
		inets:stop(),
		decode(yahoo, Body, Symbol);
	_ -> error
end;

read_source(markitondemand, URL, Symbol) ->  
	inets:start(),
	decode(markitondemand, httpc:request(get, {URL,[]},[],[]), Symbol).

%% Decodes JSON data binaries, removing unwanted values and names etc.

decode(google, Body, Symbol) ->
	String = string:sub_string(Body, 5),
	Struct = mochijson2:decode(String),
	New = Struct,
	A = [{proplists:get_value(<<"op">>, S), proplists:get_value(<<"hi">>, S),  
	proplists:get_value(<<"lo">>, S),
	proplists:get_value(<<"l_fix">>, S), proplists:get_value(<<"vo">>, S)} || {struct, S} <- New],
	recode(google, A, [], string:to_lower(Symbol));

decode(yahoo, Body, Symbol) ->
	Struct = mochijson2:decode(Body),
	{struct, JsonData} = Struct, 
	{struct, Data} = proplists:get_value(<<"query">>, JsonData), 
	{struct, New} = proplists:get_value(<<"results">>, Data), 
	New1 = [proplists:get_value(<<"quote">>, New)],
	A = [{proplists:get_value(<<"Open">>, S), proplists:get_value(<<"DaysHigh">>, S),  
	proplists:get_value(<<"DaysLow">>, S),
	proplists:get_value(<<"LastTradePriceOnly">>, S), proplists:get_value(<<"Volume">>, S)} || {struct, S} <- New1],
	recode(yahoo, A, [], string:to_lower(Symbol));

%%--------------------------------------------------------------------

decode(markitondemand, {ok, {{_, 200, _}, _Headers, Body}}, Symbol) ->
	inets:stop(),
	String = string:sub_string(Body, 19),
	Data = string:strip(String, both, 41),
	Struct = mochijson2:decode(Data),
	{struct, JsonData} = Struct,
	mod_recode(JsonData, string:to_lower(Symbol));

decode(markitondemand, {error, _error}, _Symbol) ->
	{error_mod}.

%% Following functions recodes all data into JSON-format and skips data with empty, or null values.


mod_recode([{<<"Data">>,{struct, Data}}], Symbol) ->
	case Data of
		%% Skipping stocks returning 0.
			[{_,_},{_,_},{_,_},{_, 0},{_,_},{_,_},{_,_},{_,_},{_,_},{_,_},{_,_},{_,_},{_,_},{_,_}] -> skip;
			_-> String = functions:convert(Data, ""),
				request(String, Symbol)
	end;

mod_recode([{<<"Message">>,_error}], _Symbol) ->
	{error_mod}.

recode(google, [], String, Symbol) ->
	request(String, Symbol);

recode(yahoo, [], String, Symbol) ->
	request(String, Symbol);

recode(google, [H|T], List, Symbol) ->
	recode(google, H, T, List, Symbol);

recode(yahoo, [H|T], List, Symbol) ->
		recode(yahoo, H, T, List, Symbol).

recode(google, {null,_H2,_H3,_H4,_H5}, _T, _String, _Symbol) ->
	skip;

recode(google, {_H1,null,_H3,_H4,_H5}, _T, _String, _Symbol) ->
	skip;

recode(google, {_H1,_H2,null,_H4,_H5}, _T, _String, _Symbol) ->
	skip;

recode(google, {_H1,_H2,_H3,null,_H5}, _T, _String, _Symbol) ->
	skip;

recode(google, {_H1,_H2,_H3,_H4,null}, _T, _String, _Symbol) ->
	skip;


recode(google, {H1,H2,H3,H4,H5}, T, String, Symbol) ->
X =	"{\"Time\":" ++ "\"" ++ functions:epoch() ++ "\"," ++ "\"Open\":" ++ "\"" ++ binary_to_list(H1) ++ "\"," ++ "\"High\":" ++ "\"" ++ binary_to_list(H2) ++ "\"," ++ "\"Low\":" ++ "\"" ++ binary_to_list(H3) ++ "\"," ++ "\"Close\":" ++ "\"" ++ binary_to_list(H4) ++ "\"," ++ "\"Volume\":" ++ "\"" ++ functions:volumeparse(binary_to_list(H5)) ++"\"" ++ "}}",	
	recode(google, T, String ++X, Symbol);

recode(yahoo, {null,_H2,_H3,_H4,_H5}, _T, _String, _Symbol) ->
	skip;

recode(yahoo, {_H1,null,_H3,_H4,_H5}, _T, _String, _Symbol) ->
	skip;

recode(yahoo, {_H1,_H2,null,_H4,_H5}, _T, _String, _Symbol) ->
	skip;

recode(yahoo, {_H1,_H2,_H3,null,_H5}, _T, _String, _Symbol) ->
	skip;

recode(yahoo, {_H1,_H2,_H3,_H4,null}, _T, _String, _Symbol) ->
	skip;

recode(yahoo, {H1,H2,H3,H4,H5}, T, String, Symbol) ->
X =	"{\"Time\":" ++ "\"" ++ functions:epoch() ++ "\"," ++ "\"Open\":" ++ "\"" ++ binary_to_list(H1) ++ "\"," ++ "\"High\":" ++ "\"" ++ binary_to_list(H2) ++ "\"," ++ "\"Low\":" ++ "\"" ++ binary_to_list(H3) ++ "\"," ++ "\"Close\":" ++ "\"" ++ binary_to_list(H4) ++ "\"," ++ "\"Volume\":" ++ "\"" ++ binary_to_list(H5) ++"\"" ++ "}}",
	recode(yahoo, T, String ++X, Symbol).

%% Requesting to put data into database, creating documents if no data exists for a symbol, or updates existsing ones.
%% Uses a design document stored in couchDB, specified in design_document.js

request(String, Symbol) ->
	inets:start(),
	StockData = httpc:request(put, {"http://stockin:jupiter05@stockin.cloudant.com/stockin/_design/update/_update/update/" ++ Symbol, [], [], "{\"Stock\":" ++ String}, [{ssl,[{verify,0}]}],[]),
	%StockData = httpc:request(put, {"http://127.0.0.1:5984/projects/_design/update/_update/update/" ++ Symbol, [], [], "{\"Stock\":" ++ String}, [{ssl,[{verify,0}]}],[]),
	inets:stop(),
	io:format("~p", [StockData]).

