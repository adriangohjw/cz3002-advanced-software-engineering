FactoryBot.define do
  factory :product_category do
    product_category = Hash[ProductCategory::OPTIONS.to_a.sample(1)]
    name { product_category.values[0][:name] }
    check_required { product_category.values[0][:check_required] }
  end
end
