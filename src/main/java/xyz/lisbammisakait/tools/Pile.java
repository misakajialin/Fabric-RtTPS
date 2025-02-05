package xyz.lisbammisakait.tools;

import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import xyz.lisbammisakait.RelightTheThreePointStrategy;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Pile {
    double x;
    double z;
    public static double getMinDistance(Collection<? extends Entity> entities, ServerWorld world, Pile[] piles, int maxY, boolean respectTeams) {
        double d = (double)0.0F;
        int i = 0;
        Map<AbstractTeam,Pile> map = Maps.newHashMap();

        for(Entity entity : entities) {
            Pile pile;
            if (respectTeams) {
                AbstractTeam abstractTeam = entity instanceof PlayerEntity ? entity.getScoreboardTeam() : null;
                if (!map.containsKey(abstractTeam)) {
                    map.put(abstractTeam, piles[i++]);
                }

                pile = (Pile)map.get(abstractTeam);
            } else {
                pile = piles[i++];
            }

            entity.teleport(world, (double)MathHelper.floor(pile.x) + (double)0.5F, (double)pile.getY(world, maxY), (double)MathHelper.floor(pile.z) + (double)0.5F, Set.of(), entity.getYaw(), entity.getPitch(), true);
            double e = Double.MAX_VALUE;

            for(Pile pile2 : piles) {
                if (pile != pile2) {
                    double f = pile.getDistance(pile2);
                    e = Math.min(f, e);
                }
            }

            d += e;
        }

        if (entities.size() < 2) {
            return (double)0.0F;
        } else {
            d /= (double)entities.size();
            return d;
        }
    }
    public static void spread(Vec2f center, double spreadDistance, ServerWorld world, Random random, double minX, double minZ, double maxX, double maxZ, int maxY, Pile[] piles)  {
        boolean bl = true;
        double d = (double)Float.MAX_VALUE;
        int i;
        for(i = 0; i < 10000 && bl; ++i) {
            bl = false;
            d = (double)Float.MAX_VALUE;

            for(int j = 0; j < piles.length; ++j) {
                Pile pile = piles[j];
                int k = 0;
                Pile pile2 = new Pile();

                for(int l = 0; l < piles.length; ++l) {
                    if (j != l) {
                        Pile pile3 = piles[l];
                        double e = pile.getDistance(pile3);
                        d = Math.min(e, d);
                        if (e < spreadDistance) {
                            ++k;
                            pile2.x += pile3.x - pile.x;
                            pile2.z += pile3.z - pile.z;
                        }
                    }
                }

                if (k > 0) {
                    pile2.x /= (double)k;
                    pile2.z /= (double)k;
                    double f = pile2.absolute();
                    if (f > (double)0.0F) {
                        pile2.normalize();
                        pile.subtract(pile2);
                    } else {
                        pile.setPileLocation(random, minX, minZ, maxX, maxZ);
                    }

                    bl = true;
                }

                if (pile.clamp(minX, minZ, maxX, maxZ)) {
                    bl = true;
                }
            }

            if (!bl) {
                for(Pile pile2 : piles) {
                    if (!pile2.isSafe(world, maxY)) {
                        pile2.setPileLocation(random, minX, minZ, maxX, maxZ);
                        bl = true;
                    }
                }
            }
        }

        if (d == (double)Float.MAX_VALUE) {
            d = (double)0.0F;
        }
        if (i >= 10000) {
//            if (respectTeams) {
//                throw FAILED_TEAMS_EXCEPTION.create(piles.length, center.x, center.y, String.format(Locale.ROOT, "%.2f", d));
//            } else {
//                throw FAILED_ENTITIES_EXCEPTION.create(piles.length, center.x, center.y, String.format(Locale.ROOT, "%.2f", d));
//            }
            RelightTheThreePointStrategy.LOGGER.info("error");
        }
    }
    public static Pile[] makePiles(Random random, int count, double minX, double minZ, double maxX, double maxZ) {
        Pile[] piles = new Pile[count];

        for(int i = 0; i < piles.length; ++i) {
            Pile pile = new Pile();
            pile.setPileLocation(random, minX, minZ, maxX, maxZ);
            piles[i] = pile;
        }

        return piles;
    }
    Pile() {
    }

    double getDistance(Pile other) {
        double d = this.x - other.x;
        double e = this.z - other.z;
        return Math.sqrt(d * d + e * e);
    }

    void normalize() {
        double d = this.absolute();
        this.x /= d;
        this.z /= d;
    }

    double absolute() {
        return Math.sqrt(this.x * this.x + this.z * this.z);
    }

    public void subtract(Pile other) {
        this.x -= other.x;
        this.z -= other.z;
    }

    public boolean clamp(double minX, double minZ, double maxX, double maxZ) {
        boolean bl = false;
        if (this.x < minX) {
            this.x = minX;
            bl = true;
        } else if (this.x > maxX) {
            this.x = maxX;
            bl = true;
        }

        if (this.z < minZ) {
            this.z = minZ;
            bl = true;
        } else if (this.z > maxZ) {
            this.z = maxZ;
            bl = true;
        }

        return bl;
    }

    public int getY(BlockView blockView, int maxY) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(this.x, (double)(maxY + 1), this.z);
        boolean bl = blockView.getBlockState(mutable).isAir();
        mutable.move(Direction.DOWN);

        boolean bl3;
        for(boolean bl2 = blockView.getBlockState(mutable).isAir(); mutable.getY() > blockView.getBottomY(); bl2 = bl3) {
            mutable.move(Direction.DOWN);
            bl3 = blockView.getBlockState(mutable).isAir();
            if (!bl3 && bl2 && bl) {
                return mutable.getY() + 1;
            }

            bl = bl2;
        }

        return maxY + 1;
    }

    public boolean isSafe(BlockView world, int maxY) {
        BlockPos blockPos = BlockPos.ofFloored(this.x, (double)(this.getY(world, maxY) - 1), this.z);
        BlockState blockState = world.getBlockState(blockPos);
        return blockPos.getY() < maxY && !blockState.isLiquid() && !blockState.isIn(BlockTags.FIRE);
    }

    public void setPileLocation(Random random, double minX, double minZ, double maxX, double maxZ) {
        this.x = MathHelper.nextDouble(random, minX, maxX);
        this.z = MathHelper.nextDouble(random, minZ, maxZ);
    }
}
