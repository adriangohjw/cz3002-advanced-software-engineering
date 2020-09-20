class CreateDiscounts < ActiveRecord::Migration[5.2]
  def change
    create_table :discounts do |t|
      t.references :product, foreign_key: true
      t.datetime :from_date
      t.datetime :to_date

      t.timestamps
    end
  end
end
