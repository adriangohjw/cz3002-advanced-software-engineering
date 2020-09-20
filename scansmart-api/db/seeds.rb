# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: 'Star Wars' }, { name: 'Lord of the Rings' }])
#   Character.create(name: 'Luke', movie: movies.first)

require 'factory_bot_rails'

3.times do
  FactoryBot.create(:store)
end

10.times do |count|
  shopper = FactoryBot.create(:user, type: "Shopper",
                                     email: "shopper_#{count+1}@example.com",
                                     password: "password")
  rand(1..5).times do 
    FactoryBot.create(:order, user: shopper,
                              store: Store.find(rand(1..Store.count)))
  end
end

3.times do |count|
  FactoryBot.create(:user, type: "Staff",
                           email: "staff_#{count+1}@example.com",
                           password: "password")
end

ProductCategory::OPTIONS.each do |category|
  FactoryBot.create(:product_category, name: category[1][:name],
                                       check_required: category[1][:check_required])
end