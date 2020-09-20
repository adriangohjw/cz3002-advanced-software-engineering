FactoryBot.define do
  factory :order_product do
    order { nil }
    product { nil }
    total_undiscounted_product_price { nil }
    quantity { 1 }
    total_discount_amount { 0 }
  end
end
