package dcdmod.Power;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SuperRegenPower extends AbstractPower {
	public static final String POWER_ID = "SuperRegenPower";
	private static final PowerStrings powerStrings;
	public static final String NAME;
	public static final String[] DESCRIPTIONS;

	public SuperRegenPower(AbstractCreature owner, int heal) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = heal;
		this.updateDescription();
		this.img = ImageMaster.loadImage("img/powers/WartimeReply.png");
		this.loadRegion("SuperRegenPower");
		this.type = PowerType.BUFF;
	}

	public void updateDescription() {
		if((this.owner.hasPower("KuugaDragonPower")||this.owner.hasPower("KuugaTitanPower")||this.owner.hasPower("KuugaPegasusPower")||this.owner.hasPower("RisingDragonPower")||this.owner.hasPower("RisingTitanPower")||this.owner.hasPower("RisingPegasusPower"))&&!this.owner.hasPower("RisingMightyPower")) {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +  DESCRIPTIONS[2];
		}
		else if(!this.owner.hasPower("KamenRideKuugaPower")) {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +  DESCRIPTIONS[2];
		}
		else {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +  DESCRIPTIONS[3];
		}
	}

	public void atStartOfTurn() {
		this.updateDescription();
		if((this.owner.hasPower("KuugaDragonPower")||this.owner.hasPower("KuugaTitanPower")||this.owner.hasPower("KuugaPegasusPower")||this.owner.hasPower("RisingDragonPower")||this.owner.hasPower("RisingTitanPower")||this.owner.hasPower("RisingPegasusPower"))&&!this.owner.hasPower("RisingMightyPower")) {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "SuperRegenPower", 1));
		}
		else if(!this.owner.hasPower("KamenRideKuugaPower")) {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "SuperRegenPower", 1));
		}
	}
	
	public void atEndOfTurn(boolean isPlayer) {
		this.updateDescription();
		AbstractDungeon.actionManager.addToTop(new RegenAction(this.owner, this.amount));
	}

	public void stackPower(int stackAmount) {
		super.stackPower(stackAmount);
	}

	static {
	       powerStrings = CardCrawlGame.languagePack.getPowerStrings("SuperRegenPower");
	       NAME = SuperRegenPower.powerStrings.NAME;
	       DESCRIPTIONS = SuperRegenPower.powerStrings.DESCRIPTIONS;
	}
}