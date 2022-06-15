package mokele.mbembe.common.entity.goal;

import mokele.mbembe.common.entity.Twerker;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TwerkerNavigation<T extends MobEntity & Twerker> extends MobNavigation {
    public TwerkerNavigation(T mobEntity, World world) {
        super(mobEntity, world);
    }

    @Override
    public boolean startMovingAlong(@Nullable Path path, double speed) {
        if(((Twerker)this.entity).isTwerking()) {
            return false;
        }
        return super.startMovingAlong(path, speed);
    }

    @Override
    public boolean isIdle() {
        return super.isIdle();
    }
}
