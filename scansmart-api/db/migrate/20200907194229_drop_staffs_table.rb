class DropStaffsTable < ActiveRecord::Migration[5.2]
  def up
    drop_table :staffs
  end

  def down
    raise ActiveRecord::IrreversibleMigration
  end
end
