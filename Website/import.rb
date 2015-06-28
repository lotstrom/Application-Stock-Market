require "rubygems"
require 'couchrest'


# import to database

DB = CouchRest.database("http://127.0.0.1:5984/stocks")
require 'json'
data = JSON.parse(File.read('data2.json'))

STOCKS = File.readlines("stocks.txt").each.map do |line|
	symbol,name = line.rstrip.split("\t")
	stock = {
		:Stock => {
			:Symbol => symbol, 
			:Data => data
		}
	}
	DB.save_doc(stock)
end