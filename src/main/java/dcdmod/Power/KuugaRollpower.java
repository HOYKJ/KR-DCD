package dcdmod.Power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class KuugaRollpower extends AbstractPower
 {
  public static final String POWER_ID = "KuugaRollpower";
  private static final PowerStrings powerStrings;
  public static final String NAME;
  public static final String[] DESCRIPTIONS;

  
   public KuugaRollpower(AbstractCreature owner, int amt)
   {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
    this.amount = -1;
    this.img = ImageMaster.loadImage("img/powers/Track.png");
   updateDescription();
   loadRegion("KuugaRollpower");
 }

  
   public void updateDescription() {
		 this.description = DESCRIPTIONS[0];
   }
   
   public int onAttacked(final DamageInfo info, final int damageAmount) {
	   if((info.type != DamageInfo.DamageType.THORNS) && (info.type != DamageInfo.DamageType.HP_LOSS) && (info.owner != null) && (info.owner != this.owner) && (this.owner.hasPower("KamenRideKuugaPower"))) {
		   AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(info.owner,this.owner, new KuugaSpecialPower(info.owner,1), 1));
		   if(this.owner.hasPower("RisingMightyPower")) {
			   AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(info.owner,this.owner, new KuugaSpecialPower(info.owner,1), 1));
		   }
	   }
	   return damageAmount;
   }
 
   public void atStartOfTurn() { 
		   if (this.amount == 0) {
			   AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "KuugaRollpower"));
		   } 
		   else {
			   AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "KuugaRollpower", this.amount));
		   }
   }
 
   
   static {
       powerStrings = CardCrawlGame.languagePack.getPowerStrings("KuugaRollpower");
       NAME = KuugaRollpower.powerStrings.NAME;
       DESCRIPTIONS = KuugaRollpower.powerStrings.DESCRIPTIONS;
   }
   
 }