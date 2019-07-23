package dcdmod.Vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import dcdmod.Characters.Decade;

/**
 * @author hoykj
 */
public class FAR_kick extends AbstractGameEffect {
    private int stage;
    private AbstractCreature source, target;
    private Vector2 start, current;

    public FAR_kick(AbstractCreature source, AbstractCreature target) {
        this.duration = 3.07F;
        this.startingDuration = this.duration;
        this.stage = 0;
        this.source = source;
        this.target = target;
        this.start = new Vector2(source.drawX, source.drawY);
        this.current = new Vector2(source.hb.cX, source.hb.cY - source.hb.height / 2 + target.hb.height / 2);
    }

    public void update() {
        if(this.duration == this.startingDuration){

            AbstractDungeon.effectsQueue.add(new FAR_Card(this.source, this.target));
        }

        this.duration -= Gdx.graphics.getDeltaTime();

        if((this.duration < this.startingDuration - 1.2F) && (this.stage == 0)){
            final Decade Decade = (Decade) AbstractDungeon.player;
            Decade.Trickster(65);//中途切换模型
            this.stage ++;
        }

        if((this.duration < this.startingDuration - 1.2F) && (this.duration > this.startingDuration - 1.5F)){
            this.source.drawY += 1000 * Settings.scale * Gdx.graphics.getDeltaTime();
            this.current.y += 1000 * Settings.scale * Gdx.graphics.getDeltaTime();
        }
        else if((this.duration < this.startingDuration - 1.5F) && (this.duration > this.startingDuration - 1.87F)){
            if(this.stage == 1) {
                final Decade Decade = (Decade) AbstractDungeon.player;
                Decade.Trickster(66);//中途切换模型
                this.stage++;
            }
        }
        else if((this.duration < this.startingDuration - 1.87F) && (this.duration > 0.4F)){
            if(this.stage == 2) {
                final Decade Decade = (Decade) AbstractDungeon.player;
                Decade.Trickster(68);//中途切换模型
                this.stage++;
            }
            this.source.drawX += (this.target.hb.cX - this.current.x) / 0.6 * Gdx.graphics.getDeltaTime();
            this.source.drawY += (this.target.hb.cY - this.current.y) / 0.6 * Gdx.graphics.getDeltaTime();
        }
        else if((this.duration <= 0.4F) && (this.stage == 3)){
            final Decade Decade = (Decade) AbstractDungeon.player;
            Decade.Trickster(67);//中途切换模型
            this.stage ++;
        }

        if (this.duration <= 0.0F) {
            this.isDone = true;
            final Decade Decade = (Decade)AbstractDungeon.player;
            Decade.Trickster(1);//时间结束后切换回原本模型
            this.source.drawX = this.start.x;
            this.source.drawY = this.start.y;
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
