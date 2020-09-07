class CreateOrders < ActiveRecord::Migration[5.2]
  def change
    create_table :orders do |t|
      t.references :user, foreign_key: true
      t.references :store, foreign_key: true
      t.boolean :is_verified

      t.timestamps
    end
  end
end
