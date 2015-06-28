%% @Authors: Ale Lotström 

%% Starting module. Launches backend.

-module(app).

-export([start/0, parse/1, stop/0]).

start() ->
	error_logger:tty(false),
    error_logger:logfile({open, log_report}),
	super:start_link().


parse(google) ->
	server:parse(google);
	
parse(yahoo) ->
	server:parse(yahoo);

parse(markitondemand) ->
	server:parse(markitondemand).

stop() ->
	ok.
