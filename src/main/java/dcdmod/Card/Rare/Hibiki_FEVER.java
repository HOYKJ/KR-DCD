package dcdmod.Card.Rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dcdmod.DCDmod;
import dcdmod.Patches.AbstractCardEnum;
import dcdmod.Patches.AbstractCustomCardWithType;
import dcdmod.Patches.HibikiTaikoKeyEvent;
import dcdmod.Power.HibikiKurenaiSpecialPower;



public class Hibiki_FEVER extends AbstractCustomCardWithType{
	
	public static final String ID = "Hibiki_FEVER";
	private static final CardStrings cardStrings;
	public static final String NAME;
	public static final	String DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION;
	public static final String IMG_PATH = "img/cards/FEVER.png";
	private static final int COST = 3;
	
	
	public Hibiki_FEVER() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.DCD,
        		AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF,CardColorType.Hibiki);
		this.tags.add(DCDmod.RiderCard);
		this.tags.add(DCDmod.KamenRide);
		exhaust = true;
	}
	
	@Override
    public void use(AbstractPlayer p, AbstractMonster m) {
		if(p.hasPower("HibikiKurenaiPower")&&p.hasPower("HibikiKurenaiSpecialPower")) {
			int x = p.getPower("HibikiKurenaiSpecialPower").amount;
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HibikiKurenaiSpecialPower(p,x),x));
		}
		else {
			HibikiTaikoKeyEvent.ComboPoint += 10;
		}
	}
	
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if(!canUse) return false;
		if(!p.hasPower("KamenRideHibikiPower")) {
			canUse = false;
			this.cantUseMessage = EXTENDED_DESCRIPTION[0];
		}
		return canUse;
	}
	
	
	@Override
    public AbstractCard makeCopy() {
        return new Hibiki_FEVER();
    }
	
	@Override
	public void optionDecade() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void optionKuuga() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void optionAgito() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void optionRyuki() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void optionFaiz() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void optionBlade() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void optionHibiki() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void optionKabuto() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void optionDenO() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void optionKiva() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void optionNeutral() {

	}
    
	@Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
	
    }
    
    static {
    	cardStrings = CardCrawlGame.languagePack.getCardStrings("Hibiki_FEVER");
        NAME = Hibiki_FEVER.cardStrings.NAME;
        DESCRIPTION = Hibiki_FEVER.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Hibiki_FEVER.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = Hibiki_FEVER.cardStrings.EXTENDED_DESCRIPTION;
    }

	@Override
	public void energychange() {
		if(this.freeToPlayOnce){
			setBannerTexture(DCDmod.RARE[0], DCDmod.RARE_P[0]);
		}
		else if(this.costForTurn == -1 || this.costForTurn > 5) {
			setBannerTexture(DCDmod.RARE[6], DCDmod.RARE_P[6]);
		}
		else {
			int cost = this.costForTurn;
			setBannerTexture(DCDmod.RARE[cost], DCDmod.RARE_P[cost]);
		}
	}

	
	
}
