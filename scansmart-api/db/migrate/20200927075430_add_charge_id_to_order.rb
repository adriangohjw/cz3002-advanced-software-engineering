class AddChargeIdToOrder < ActiveRecord::Migration[5.2]
  def change
    add_column :orders, :stripe_charge_id, :string
  end
end
