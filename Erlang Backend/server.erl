%% @Author: Ale Lotström
%% This module starts gen_servers and handles calls, casts.
%% Also launches 'Parser'-module.

-module (server).

-behaviour (gen_server).

-export ([start_link/0, stop/0, parse/1]).

-export([init/1, handle_call/3, handle_cast/2, handle_info/2,
	 terminate/2, code_change/3]).

-define(SERVER, ?MODULE). 

%%---------------Client Call----------------------------------

start_link() ->
    gen_server:start_link({local, ?SERVER}, ?MODULE, [], []).

stop() ->
	gen_server:cast(server, stop).

parse(google) ->
	gen_server:cast(server, {parse, google});

parse(yahoo) ->
	gen_server:cast(server, {parse, yahoo});

parse(markitondemand) ->
	gen_server:cast(server, {parse, markitondemand}).

%%---------------Call backs------------------------------------

init([]) ->
	{ok, []}.

handle_call(_Request, _From, State) ->
   {reply, nothing, State}.


handle_cast({parse, google},State) ->
	parser:gen_url(google, functions:parse("NYSE.txt")),
	{noreply, State};

handle_cast({parse, yahoo},State) ->
	parser:gen_url(yahoo, functions:parse("NASDAQ.txt")),
	{noreply, State};

handle_cast({parse, markitondemand},State) ->
	parser:gen_url(markitondemand, functions:parse("AMEX.txt")),
	{noreply, State}.

handle_info(_Info, State) ->
    {noreply, State}.

terminate(_Reason, _State) ->
	ok.

code_change(_OldVersion, State, _Extra) ->
	{ok, State}.




