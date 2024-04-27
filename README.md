This plugin changes nothing about the actual spawner
It simply adds a permission attachment to players when they break a spawner
And then Rosestacker handles the breaking and manipulating of the spawner block

You must have the following set in Rosestacker's config.yml file
To find these use Ctrl+f and search for "silk-touch-advanced-permissions" and "silk-touch-required"

  # Should advanced silk touch permissions be used?
  # Requires silk-touch-require-permission to be enabled for the silk touch permissions
  # This will enable the following permissions:
  # - rosestacker.silktouch.<entityType>
  # - rosestacker.nosilk.<entityType>
  # - rosestacker.spawnerplace.<entityType>
  # Default: false
  silk-touch-advanced-permissions: true

  # Should silk touch be required to pick up spawners?
  # Default: false
  silk-touch-required: true
