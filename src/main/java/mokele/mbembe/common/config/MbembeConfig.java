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



    @Override
    public String getName() {
        return "hyperion";
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
