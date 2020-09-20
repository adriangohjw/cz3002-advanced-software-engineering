FactoryBot.define do
  factory :inventory do
    store { nil }
    product { nil }
    quantity { rand(0..100) }
  end
end
