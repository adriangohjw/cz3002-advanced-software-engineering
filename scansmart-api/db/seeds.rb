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

stores = Store.all
ProductCategory::OPTIONS.each do |category|
  product_category = FactoryBot.create(:product_category, name: category[1][:name],
                                                          check_required: category[1][:check_required])

  10.times do
    product = FactoryBot.create(:product, product_category: product_category)

    if rand(0..100)/100.0 < 0.1   # 10% chance of creating discount
      if [true, false].sample
        FactoryBot.create(:discount_single, product: product,
                                            price: product.price * rand(50...100)/100.0)   # discount ranges from 1% - 50%
      else
        bulk_quantity = rand(2..5)
        FactoryBot.create(:discount_bulk, product: product,
                                          bulk_quantity: bulk_quantity,
                                          bulk_price: product.price * bulk_quantity * rand(50...100)/100.0)   # discount ranges from 1% - 50%
      end
    end

    stores.each do |store|
      if rand(0..100)/100.0 > 0.2   # 80% chance of product found in a particular store
        FactoryBot.create(:inventory, product: product,
                                      store: store,
                                      quantity: rand(1..100))
      end
    end
  end
end

10.times do |count|
  shopper = FactoryBot.create(:user, type: "Shopper",
                                     email: "shopper_#{count+1}@example.com",
                                     password: "password")
  rand(1..5).times do 
    FactoryBot.create(:order, user: shopper,
                              store: Store.find(rand(1..Store.count)))
  end

  products_in_cart = Product.all.sample(rand(0..5))
  products_in_cart.each do |product|
    FactoryBot.create(:cart_product, user: shopper,
                                     product: product)
  end
end

3.times do |count|
  FactoryBot.create(:user, type: "Staff",
                           email: "staff_#{count+1}@example.com",
                           password: "password")
end

