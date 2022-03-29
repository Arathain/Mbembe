package mokele.mbembe.common.entity.goal;

import mokele.mbembe.common.entity.Twerker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;

import java.util.EnumSet;

public class TwerkGoal extends Goal {
    private final PathAwareEntity twerka;

    public TwerkGoal(PathAwareEntity twerker) {
        this.twerka = twerker;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.JUMP));
    }

    @Override
    public boolean canStart() {
        return ((Twerker)twerka).isTwerking();
    }

    @Override
    public void start() {
        this.twerka.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.twerka.getNavigation().stop();
        super.tick();
    }
}
