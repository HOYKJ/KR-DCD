 package dcdmod.Actions;
 
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
 
 public class PhotonAction extends AbstractGameAction
 {
   private AbstractCard c;
   public PhotonAction(AbstractCard c) {
	 this.c = c;
     this.duration = Settings.ACTION_DUR_LONG;
     this.actionType = AbstractGameAction.ActionType.SPECIAL;
     
   }
   
   public void update() {   	
	     if (this.duration == Settings.ACTION_DUR_LONG) {
	 		this.c.modifyCostForCombat(1);
			this.c.isCostModified = true;
	  		tickDuration();

	      }  
	      tickDuration();
	}
 }



