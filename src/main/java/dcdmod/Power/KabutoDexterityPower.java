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
 
 public class KabutoDexterityPower extends AbstractPower
 {
   public static final String POWER_ID = "KabutoDexterityPower";
   private static final PowerStrings powerStrings;
   public static final String NAME;
   public static final String[] DESCRIPTIONS;


   
   public KabutoDexterityPower(AbstractCreature owner, int newAmount) {
     this.name = NAME;
     this.ID = POWER_ID;
     this.owner = owner;
     this.amount = -1;
     this.img = ImageMaster.loadImage("img/powers/Exercise2.png");
     updateDescription();
     loadRegion("KabutoDexterityPower");
   }
   
   public void updateDescription() {	   
	   this.description = DESCRIPTIONS[0];
	}
   
   public void onUseCard(final AbstractCard card, final UseCardAction action) {
		if(this.owner.hasPower("KamenRideKabutoPower")) {
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "KabutoDexterityPower")); 
		}
    }
   
	public float modifyBlock(float blockAmount) {
		return blockAmount * 4;
	}

   
   static {
	     powerStrings = CardCrawlGame.languagePack.getPowerStrings("KabutoDexterityPower");
	     NAME = KabutoDexterityPower.powerStrings.NAME;
	     DESCRIPTIONS = KabutoDexterityPower.powerStrings.DESCRIPTIONS;
	 }
   
   }
  


