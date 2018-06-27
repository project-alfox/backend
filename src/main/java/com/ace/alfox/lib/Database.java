package com.ace.alfox.lib;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import com.ace.alfox.game.models.Location;
import com.ace.alfox.game.models.Player;
import javax.annotation.PreDestroy;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteBuilder;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Database {
  public static final String USE_IN_MEMORY = "DON'T PERSIST TO DISK";
  final Nitrite db;

  public ObjectRepository<Player> players;
  public ObjectRepository<Location> locations;

  /**
   * Open the given database or create it if it doesn't exist. Only one instance can have a database
   * open at a time.
   *
   * @param name The file path to the database, if the passed string is equal to
   *     {Database.USE_IN_MEMORY} the database is initialized in MemoryOnly mode and not persisted
   *     to disk. Useful for tests.
   */
  public Database(@Value(value = "game") String name) {
    NitriteBuilder _db = Nitrite.builder();
    if (!name.equals(USE_IN_MEMORY)) {
      _db = _db.filePath(name + ".db");
    }
    db = _db.openOrCreate();
    players = db.getRepository(Player.class);
    locations = db.getRepository(Location.class);
  }

  public Location findLocation(Vector2 coordinates) {
    Location result = locations.find(eq("coordinates", coordinates)).firstOrDefault();
    if (result == null) {
      // HACK I should look up the zone this location is in and provide the default
      result = new Location();
    }
    return result;
  }

  @PreDestroy
  public void destroy() {
    db.close();
  }
}
