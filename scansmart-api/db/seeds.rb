# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: 'Star Wars' }, { name: 'Lord of the Rings' }])
#   Character.create(name: 'Luke', movie: movies.first)

require 'factory_bot_rails'

10.times do |count|
  FactoryBot.create(:user, type: "Shopper",
                           email: "shopper_#{count+1}@example.com",
                           password: "password")
end

3.times do |count|
  FactoryBot.create(:user, type: "Staff",
                           email: "staff_#{count+1}@example.com",
                           password: "password")
end

3.times do
  FactoryBot.create(:store)
end