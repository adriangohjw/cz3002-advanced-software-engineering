class CreateMovements < ActiveRecord::Migration[5.2]
  def change
    create_table :movements do |t|
      t.string :movement_type
      t.integer :user_id
      t.integer :store_id

      t.timestamps
    end
  end
end
