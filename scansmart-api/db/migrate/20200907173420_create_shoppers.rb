class CreateShoppers < ActiveRecord::Migration[5.2]
  def change
    create_table :shoppers do |t|

      t.timestamps
    end
  end
end
