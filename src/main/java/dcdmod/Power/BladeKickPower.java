 package dcdmod.Power;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

 
 public class BladeKickPower extends AbstractPower
 {
	  public static final String POWER_ID = "BladeKickPower";
	  private static final PowerStrings powerStrings;
	  public static final String NAME;
	  public static final String[] DESCRIPTIONS;
	   public BladeKickPower(AbstractCreature owner, int amt)
	   {
		   
	    this.name = NAME;
	    this.ID = POWER_ID;
	    this.owner = owner;
	    this.amount = -1;
	    this.img = ImageMaster.loadImage("img/powers/BladeKickPower.png");
	    
	   updateDescription();
	   loadRegion("BladeKickPower");
	   }
	   
	   public void onAfterUseCard(final AbstractCard card, final UseCardAction action) {
		   if(card.cardID == "FinalAttackRide") {
			   AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "BladeKickPower"));
		   }
	   }
	   
	   public void updateDescription() {
			 this.description = DESCRIPTIONS[0];}
	   
	   static {
	       powerStrings = CardCrawlGame.languagePack.getPowerStrings("BladeKickPower");
	       NAME = BladeKickPower.powerStrings.NAME;
	       DESCRIPTIONS = BladeKickPower.powerStrings.DESCRIPTIONS;
	   }
	   
	 
	   
	  
	  }

