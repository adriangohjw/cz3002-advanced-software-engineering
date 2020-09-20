class CreateOrderProducts < ActiveRecord::Migration[5.2]
  def change
    create_table :order_products do |t|
      t.references :order, foreign_key: true
      t.references :product, foreign_key: true
      t.float :total_undiscounted_product_price
      t.integer :quantity
      t.float :total_discount_amount

      t.timestamps
    end
  end
end
