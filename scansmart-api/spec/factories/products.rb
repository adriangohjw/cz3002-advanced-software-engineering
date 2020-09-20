require 'faker'

FactoryBot.define do
  factory :product do
    product_category { nil }
    name { "#{Faker::Company.name} #{Faker::Food.dish}" }
    price { rand(1..100) }
    barcode { Faker::Barcode.ean(13) }
  end
end
