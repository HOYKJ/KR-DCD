 package dcdmod.Power;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

 
 public class MirrorWorldPower extends AbstractPower
 {
	  public static final String POWER_ID = "MirrorWorldPower";
	  private static final PowerStrings powerStrings;
	  public static final String NAME;
	  public static final String[] DESCRIPTIONS;
	   public MirrorWorldPower(AbstractCreature owner, int amt)
	   {
		   
	    this.name = NAME;
	    this.ID = POWER_ID;
	    this.owner = owner;
	    this.amount = -1;
	    this.img = ImageMaster.loadImage("img/powers/MirrorWorldPower.png");
	    
	   updateDescription();
	   loadRegion("MirrorWorldPower");
	   }
	   
	   public void atEndOfTurn(final boolean isPlayer) {
		   AbstractDungeon.player.currentHealth =  (AbstractDungeon.player.currentHealth-((AbstractDungeon.player.maxHealth*10)/100));
		   AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner,new DamageInfo(this.owner, 0, DamageType.HP_LOSS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		   for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
				 if ((!monster.isDead) && (!monster.isDying)) {
					 monster.currentHealth = (monster.currentHealth-((monster.maxHealth*10)/100));
					 AbstractDungeon.actionManager.addToBottom(new DamageAction(monster,new DamageInfo(this.owner, 0, DamageType.HP_LOSS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
				 }
			}

	   }
	   
	   public void onRemove() {
			for(AbstractRelic r: AbstractDungeon.player.relics) {
				if(r.relicId == "MirrorWorldRelic") {
					AbstractDungeon.player.relics.remove(r);
					break;
				}
			}
	   }
	   
	   public void onVictory() {
			for(AbstractRelic r: AbstractDungeon.player.relics) {
				if(r.relicId == "MirrorWorldRelic") {
					AbstractDungeon.player.relics.remove(r);
					break;
				}
			}
	   }
	   
	   public void updateDescription() {
			 this.description = DESCRIPTIONS[0];}
	   
	   static {
	       powerStrings = CardCrawlGame.languagePack.getPowerStrings("MirrorWorldPower");
	       NAME = MirrorWorldPower.powerStrings.NAME;
	       DESCRIPTIONS = MirrorWorldPower.powerStrings.DESCRIPTIONS;
	   }  
 }

