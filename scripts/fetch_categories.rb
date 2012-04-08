
require 'rubygems'
require 'httparty'
require 'builder'

API_KEY = "bace5d090265e765e78bf188f414783e"
FORMAT  = "json"
LANG    = ENV['EXPORT_LANG'] || 'en'

class CategoryFetcher

  include HTTParty

  base_uri "http://api.themoviedb.org/2.1/Genres.getList"

end

resp = CategoryFetcher.get("/#{LANG}/#{FORMAT}/#{API_KEY}")

categories = resp.parsed_response
categories.shift # drop the "translated = true" crap

xmlfile = File.new('tmdb_genre_names.xml', 'w')
builder = Builder::XmlMarkup.new(:target => xmlfile)
builder.instruct! :xml, :version => "1.0", :encoding => "UTF-8"
builder.resources do
  builder.tag!("string-array", :name => 'tmdb_genre_names') do
    categories.each do |cat|
      builder.item(cat['name'])
    end
  end
end
builder.target!