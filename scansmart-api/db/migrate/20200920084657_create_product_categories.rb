class CreateProductCategories < ActiveRecord::Migration[5.2]
  def change
    create_table :product_categories do |t|
      t.string :name
      t.boolean :check_required

      t.timestamps
    end
  end
end
