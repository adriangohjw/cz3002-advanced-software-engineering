class AddTypeToDiscount < ActiveRecord::Migration[5.2]
  def change
    add_column :discounts, :type, :string
  end
end
