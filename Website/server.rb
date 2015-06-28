require "rubygems"
require "sinatra"
require 'couchrest'
# DB.view("_design/ylva/_view/search",{:startkey => "a",:limit =>5})
DB = CouchRest.database("http://stockin:jupiter05@stockin.cloudant.com/stockin")
enable :logging
post '/search' do 
	query = params[:query]
	query = query.upcase 
	
	if query == ""
		""
	else
		@stocks = DB.view("_design/ylva/_view/search",{:startkey => query,:limit =>5})["rows"].map {|r|
			r["id"]
		}
		erb :search
	end

end

# root path
get '/' do
	erb :index, :layout => :main 
end
# get stock
get "/stock" do
	@symbol = params[:symbol]
	stock = DB.get(@symbol)

	@data = stock["Stock"]["Data"].map do |day|
		[
			day["Time"].to_i,
			day["Open"].to_f,
			day["High"].to_f,
			day["Low"].to_f,
			day["Close"].to_f
		]
	end
	@closes = @data.map do |day|
		[
			day[0],
			day[4]
		]
	end
	@volumes = stock["Stock"]["Data"].map do |day|
		[
			day["Time"].to_i,
			day["Volume"].to_i
		]
	end

	erb :stock, :layout => :main
end

