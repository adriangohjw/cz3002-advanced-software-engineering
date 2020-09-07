class CreateUsers < ActiveRecord::Migration[5.2]
  def change
    create_table :users do |t|
      t.string :email
      t.string :name
      t.string :password
      t.datetime :last_logged_in

      t.timestamps
    end
  end
end
