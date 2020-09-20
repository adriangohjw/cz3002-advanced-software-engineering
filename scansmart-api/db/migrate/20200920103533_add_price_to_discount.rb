class AddPriceToDiscount < ActiveRecord::Migration[5.2]
  def change
    add_column :discounts, :price, :float
  end
end
