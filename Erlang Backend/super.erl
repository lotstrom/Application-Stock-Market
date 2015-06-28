%% @Author: Ale Lotström

%% Module starts proper supervisor and children, as well as starting SSL for online database.

-module (super).

-behaviour (supervisor).

-export ([start_link/0]).

-export([init/1]).

-define(SERVER, ?MODULE).

start_link() ->
	ssl:start(),
	whereis(ssl_sup),
	supervisor:start_child(ssl_sup, {ssl_server, {ssl_server, start_link, []}, permanent, 2000, worker, [ssl_server]}),
	whereis(ssl_server),
	supervisor:start_link({global, ?MODULE}, ?MODULE, []).

init([]) ->
	io:format("~p (~p) starting... ~n",[{global, ?MODULE}, self()]),
	RestartStrategy = one_for_one,
	MaxRestarts = 100,
	MaxSecondsBetweenRestarts = 100,
	Flags = {RestartStrategy, MaxRestarts, MaxSecondsBetweenRestarts},
	Restart = permanent,
	Shutdown = infinity,
	Type = worker,
	ChildSpecifications = {?MODULE, {server, start_link, []}, Restart, Shutdown, Type, [server]},
	{ok, {Flags, [ChildSpecifications]}}.