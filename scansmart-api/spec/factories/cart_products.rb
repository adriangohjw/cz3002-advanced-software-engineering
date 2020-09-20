FactoryBot.define do
  factory :cart_product do
    user { nil }
    product { nil }
    quantity { rand(1..5) }
  end
end
