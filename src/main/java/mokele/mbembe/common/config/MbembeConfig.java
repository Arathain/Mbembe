package mokele.mbembe.common.config;

import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;
import draylar.omegaconfig.api.Syncing;
import mokele.mbembe.Mbembe;

public class MbembeConfig implements Config {
    @Syncing
    @Comment("""
            Mokele Mbembe spawning weight. Default is 1.
            """)
    public int mbembeWeight = 1;
    @Syncing
    @Comment("""
            The smallest group size Mokeles can spawn in. Default is 1.
            """)
    public int mbembeMin = 1;
    @Syncing
    @Comment("""
            The largest group size Mokeles can spawn in. Default is 1.
            """)
    public int mbembeMax = 1;
    @Syncing
    @Comment("""
            Percentage chance that a mokele won't spawn. Default is 0.95.
            """)
    public float mbembeSpawnChance = 0.95f;
    @Syncing
    @Comment("""
            Dodo spawning weight. Default is 1.
            """)
    public int dodoWeight = 1;
    @Syncing
    @Comment("""
            The smallest group size Dodos can spawn in. Default is 1.
            """)
    public int dodoMin = 1;
    @Syncing
    @Comment("""
            The largest group size Dodos can spawn in. Default is 4.
            """)
    public int dodoMax = 4;



    @Override
    public String getName() {
        return "mbembe";
    }
    @Override
    public String getModid() {
        return Mbembe.MOD_ID;
    }
    @Override
    public String getExtension() {
        return "toml";
    }
}
