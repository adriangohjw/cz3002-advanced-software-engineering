class AddBulkFieldsToDiscount < ActiveRecord::Migration[5.2]
  def change
    add_column :discounts, :bulk_quantity, :integer
    add_column :discounts, :bulk_price, :float
  end
end
