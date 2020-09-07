class DropShoppersTable < ActiveRecord::Migration[5.2]
  def up
    drop_table :shoppers
  end

  def down
    raise ActiveRecord::IrreversibleMigration
  end
end
