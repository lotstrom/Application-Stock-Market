%% This module contains various functions used by Parser module.

-module (functions).

-export ([volumeparse/1, parse/1, convert/2, epoch/0]).

%% @Authors: Ale Lotström

%% Parsing value for 'Volume' based on received format. Removing possible 
%% dots, commas, and replaces M with 0's for millions.

volumeparse("") ->
	"0";

volumeparse(Vol) ->

case string:right(Vol,1) of
	"M" -> Vol2 = string:strip(Vol, both, $M),
			[H,T] = string:tokens(Vol2, "."),
				H++T++"00000";
	_ -> volumeparse2(Vol)

end.

volumeparse2("0.00") ->
	"0";

volumeparse2(Vol2) ->
case string:right(Vol2, 3) of
	".00" -> String = string:strip(Vol2, right, $0),
			String2 = string:strip(String, right, $.),
			strip_comma(String2);
	_ -> Vol2
end.
strip_comma(Str) ->
	case string:chr(Str, $,) of
		0 ->
			Str;
		_ -> 
			[H,T] = string:tokens(Str, ", "),
			NewStr = H++T,
			NewStr
	end.

%% Parses .txt-files and returns a list of stock symbols.

%% Reads and splits a .txt-file into lines.
parse(File) ->
  {ok, Data} = file:read_file(File),
  Lines = re:split(Data, "\r|\n|\r\n",[{return, list}, {parts, 0}]),
  run(Lines, []).

%% Main function: Loops through .txt-file, storing stock symbols and 
%% disregarding other information.
run([], List) ->
List;

run([[]|T], List) ->
	run(T, List);

run([H|T], List) ->
  List ++ [store_symbol(re:split(H, "\t"))|run(T, List)].

%% Converts stock symbol from binary to list.
store_symbol([H|_]) ->
 	binary_to_list(H).

%% This module converts stock data into Strings to create a correct JSON format.


convert([], String) ->
	"{\"Time\":" ++ "\"" ++ date:epoch() ++ "\"," ++ String ++ "}}";


convert([H|T], String) ->
	json_converter(H, T, String).


%% Converts all data to strings and removes binaries. Also removing unwanted information.
json_converter({First, Second}, T, String) ->
	case First of
		<<"LastPrice">> -> convert(T, String ++ "\"Close\":" ++ "\"" ++ lists:flatten(io_lib:format("~p", [Second])) ++ "\","); 
		<<"High">> -> convert(T, String ++ "\"High\":" ++ "\"" ++ lists:flatten(io_lib:format("~p", [Second]))++ "\",");
		<<"Low">> -> convert(T, String ++ "\"Low\":" ++ "\"" ++ lists:flatten(io_lib:format("~p", [Second]))++ "\",");
		<<"Open">> -> convert(T, String ++ "\"Open\":" ++ "\"" ++ lists:flatten(io_lib:format("~p", [Second]))++ "\"");
		<<"Volume">> -> convert(T, String ++ "\"Volume\":" ++ "\"" ++ lists:flatten(io_lib:format("~p", [Second]))++ "\",");
		_ -> convert(T, String)
	end.

%% Creating date format in unix time to be added to stock data.


epoch() ->
lists:flatten(io_lib:format("~p", [calendar:datetime_to_gregorian_seconds(calendar:now_to_universal_time( now()))-719528*24*3600]) ++ "000").




