package dcdmod.Vfx;

import basemod.DevConsole;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import dcdmod.Characters.Decade;

/**
 * @author hoykj
 */
public class FAR_Card extends AbstractGameEffect {
    private AbstractCreature source, target;
    private Texture img;
    private float[] x = new float[10];
    private float[] y = new float[10];
    private boolean[] draw = new boolean[10];
    private float timer;
    private int counter;

    public FAR_Card(AbstractCreature source, AbstractCreature target){
        this.duration = 2.47F;
        this.startingDuration = this.duration;
        this.img = ImageMaster.loadImage("img/char/DCD_Animational/FAR/card.png");
        this.source = source;
        this.target = target;
        for(int i = 0; i < 10; i ++){
            this.x[i] = this.source.hb.cX + (this.target.hb.cX - this.source.hb.cX) / 11 * (i + 1) - 68.0F;
            this.y[i] = this.source.hb.cY + (this.target.hb.cY - this.source.hb.cY) / 9 * (i) - 200.0F;
            this.draw[i] = false;
        }
        this.timer = 0.1F;
        this.counter = 0;
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();

        if(this.duration >= this.startingDuration - 1.0F){
            this.timer -= Gdx.graphics.getDeltaTime();
            if(this.timer <= 0){
                this.draw[this.counter] = true;
                this.counter ++;
                this.timer += 0.1F;
            }
        }

        if((this.duration < this.startingDuration - 1.10F) && (this.duration > this.startingDuration - 1.5F)){
            for(int i = 0; i < 10; i ++){
                this.draw[i] = true;
                this.y[i] += (9 - i) * 300.0F / 9 / 0.4F * Settings.scale * Gdx.graphics.getDeltaTime();
            }
            this.counter = 0;
            this.timer = 0.06F;
        }
        else if((this.duration <= 0.66F) && (this.counter < 10)){
            this.timer -= Gdx.graphics.getDeltaTime();
            if((this.timer <= 0)){
                this.draw[this.counter] = false;
                this.counter ++;
                this.timer += 0.06F;
            }
        }

        if(this.duration <= 0){
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        for(int i = 0; i < 10; i ++) {
            if(this.draw[i]) {
                sb.draw(this.img, this.x[i], this.y[i], 68.0F, 200.0F, 136.0F, 400.0F,
                        this.scale, this.scale, this.rotation,
                        0, 0, 136, 400, false, false);
            }
        }
    }

    public void dispose() {
    }
}
