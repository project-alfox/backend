package com.ace.alfox.lib.data;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import com.ace.alfox.game.models.Player;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.dizitart.no2.objects.ObjectRepository;

public class PlayerRepository extends ObjectRepositoryFacade<Player> {

  PlayerRepository(ObjectRepository<Player> or) {
    super(or);
  }

  /**
   * * Search for a player going by the given username
   *
   * @param username target username, the first 8 bytes are used as the player id, this is a hack
   *     until Adam makes a real account system.
   * @return The found Player or null if not found
   */
  public Player find(String username) {
    // For now because we don't have a user database yet, just use the first 8 bytes of the username
    // as the playerId
    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
    buffer.put(Arrays.copyOfRange(username.getBytes(), 0, Long.BYTES));
    buffer.flip(); // reset position to 0
    Long playerId = buffer.getLong();

    Player p = find(playerId);

    /* HACK create a player if it doesn't exist, because we don't have a way yet to make new accounts properly. */
    if (p == null) {
      p = new Player();
      p.id = playerId;
      p.name = username;
      super.insert(p);
    }
    return p;
  }

  /**
   * Search for a player based on their PlayerId, their unique identifier.
   *
   * @param playerId player identifier
   * @return The Player or null if not found.
   */
  public Player find(Long playerId) {
    return super.find(eq("id", playerId)).firstOrDefault();
  }
}
