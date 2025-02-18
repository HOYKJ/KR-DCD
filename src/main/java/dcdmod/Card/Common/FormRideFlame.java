package dcdmod.Card.Common;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import basemod.abstracts.CustomSavable;
import basemod.helpers.TooltipInfo;
import dcdmod.DCDmod;
import dcdmod.Actions.RemoveFormRideAction;
import dcdmod.Actions.RemoveHalfAttributeAction;
import dcdmod.Card.Special.AgitoPower;
import dcdmod.Card.Special.FlameSpecialCard;
import dcdmod.Patches.AbstractCardEnum;
import dcdmod.Patches.AbstractCustomCardWithType;
import dcdmod.Power.AgitoFlamePower;
import dcdmod.Power.FlameLevelPower;
import dcdmod.Vfx.Agito_flamesounds;



public class FormRideFlame extends AbstractCustomCardWithType implements CustomSavable<int[]>{
	
	public static final String ID = "FormRideFlame";
	private static final CardStrings cardStrings;
	public static final String NAME;
	public static final	String DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION;
	public static final String IMG_PATH = "img/cards/FormRideFlame.png";
	private static final int COST = 1;
	private List<TooltipInfo> tips;

	public FormRideFlame() {
        this(0);
    }
	
	public FormRideFlame(int upgrades) {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.DCD,
        		AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF,CardColorType.Agito);
		
		if(upgrades>9){
			upgrades = 9;
		}
		this.timesUpgraded = upgrades;
		if(this.timesUpgraded >= 9) {
			this.timesUpgraded = 9;
		}
		this.baseMagicNumber = 1;
		this.tags.add(DCDmod.RiderCard);
		this.tags.add(DCDmod.KamenRide);
		this.tips = new ArrayList<TooltipInfo>();
		switch(this.timesUpgraded) {
		case 0:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
			break;
		case 1:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2]));
			break;
		case 2:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3]));
			break;
		case 3:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4]));
			break;
		case 4:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4] + EXTENDED_DESCRIPTION[5]));
			break;
		case 5:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4] + EXTENDED_DESCRIPTION[5] + EXTENDED_DESCRIPTION[6]));
			break;
		case 6:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4] + EXTENDED_DESCRIPTION[5] + EXTENDED_DESCRIPTION[6] + EXTENDED_DESCRIPTION[7]));
			break;
		case 7:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4] + EXTENDED_DESCRIPTION[5] + EXTENDED_DESCRIPTION[6] + EXTENDED_DESCRIPTION[7] + EXTENDED_DESCRIPTION[8]));
			break;
		case 8:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4] + EXTENDED_DESCRIPTION[5] + EXTENDED_DESCRIPTION[6] + EXTENDED_DESCRIPTION[7] + EXTENDED_DESCRIPTION[8] + EXTENDED_DESCRIPTION[9]));
			break;
		case 9:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4] + EXTENDED_DESCRIPTION[5] + EXTENDED_DESCRIPTION[6] + EXTENDED_DESCRIPTION[7] + EXTENDED_DESCRIPTION[8] + EXTENDED_DESCRIPTION[9] + EXTENDED_DESCRIPTION[10]));
			break;
		}
	}
	
	@Override
    public void use(AbstractPlayer p, AbstractMonster m) {
		if(!p.hasPower("AgitoFlamePower")) {
			AbstractDungeon.actionManager.addToTop(new RemoveHalfAttributeAction(p, p));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
		}
		if(p.hasPower("AgitoFlamePower")&&p.hasPower("AgitoStormPower")) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
		}
		if(!p.hasPower("AgitoFlamePower")||(p.hasPower("AgitoFlamePower") && p.getPower("FlameLevelPower").amount<(this.timesUpgraded+1))) {
			if(p.hasPower("AgitoStormPower")) {
				if(p.getPower("StormLevelPower").amount<=4) {
					AbstractDungeon.actionManager.addToTop(new RemoveFormRideAction(p, p));
					AbstractDungeon.actionManager.addToTop(new RemoveHalfAttributeAction(p, p));
				}
				else {
					if(p.hasPower("AgitoFlamePower")) {
						AbstractDungeon.actionManager.addToTop(new RemoveHalfAttributeAction(p, p));
					}
				}
			}
			if(this.timesUpgraded >= 9) {
				this.timesUpgraded = 9;
			}
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AgitoFlamePower(p, 1), 1));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FlameLevelPower(p,this.timesUpgraded + 1),this.timesUpgraded + 1));
			switch(this.timesUpgraded) {
			case 0:
				break;
			case 1:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FlameSpecialCard(), 1));
				break;
			case 2:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FlameSpecialCard(), 1));
				break;
			case 3:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 3), 3));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FlameSpecialCard(), 1));
				break;
			case 4:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 3), 3));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FlameSpecialCard(), 1));
				break;
			case 5:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 4), 4));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FlameSpecialCard(), 1));
				break;
			case 6:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 4), 4));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FlameSpecialCard(), 1));
				break;
			case 7:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 6), 6));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, 2), 2));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FlameSpecialCard(), 1));
				break;
			case 8:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 6), 6));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, 2), 2));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new AgitoPower(), 1));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FlameSpecialCard(), 1));
				break;
			case 9:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 6), 6));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, 2), 2));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new AgitoPower(), 1));
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FlameSpecialCard(), 1));
				break;
			default:
				break;
			}
			CardCrawlGame.sound.playA("formride", 0.0f);
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new Agito_flamesounds(p.drawX - 200.00f, p.drawY + 250.00f), 0F));
		}
	}
	
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if(!canUse) return false;
		if(!p.hasPower("KamenRideAgitoPower")) {
			canUse = false;
			this.cantUseMessage = EXTENDED_DESCRIPTION[11];
		}
		return canUse;
	}
	
	@Override
	public List<TooltipInfo> getCustomTooltips() {
		return this.tips;
	}
	
	@Override
    public AbstractCard makeCopy() {
        return new FormRideFlame(timesUpgraded);
    }
	
	
	@Override
	public void optionDecade() {
		
	}

	@Override
	public void optionKuuga() {
		
		
	}

	@Override
	public void optionAgito() {
		
		
	}

	@Override
	public void optionRyuki() {
		
		
	}

	@Override
	public void optionFaiz() {
		
		
	}

	@Override
	public void optionBlade() {
		
		
	}

	@Override
	public void optionHibiki() {
		
		
	}

	@Override
	public void optionKabuto() {
		
		
	}

	@Override
	public void optionDenO() {
		
		
	}

	@Override
	public void optionKiva() {
		
		
	}

	@Override
	public void optionNeutral() {

	}
    
	@Override
	public void upgrade(){
		upgradeMagicNumber(1);
		this.timesUpgraded += 1;
		if(this.timesUpgraded >= 9) {
			this.timesUpgraded = 9;
		}
		this.upgraded = true;
		this.name = (NAME + "Lv" + (int)(this.timesUpgraded+1));
		this.tips = new ArrayList<TooltipInfo>();
		switch(this.timesUpgraded) {
		case 0:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
			break;
		case 1:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2]));
			break;
		case 2:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3]));
			break;
		case 3:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4]));
			break;
		case 4:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4] + EXTENDED_DESCRIPTION[5]));
			break;
		case 5:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4] + EXTENDED_DESCRIPTION[5] + EXTENDED_DESCRIPTION[6]));
			break;
		case 6:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4] + EXTENDED_DESCRIPTION[5] + EXTENDED_DESCRIPTION[6] + EXTENDED_DESCRIPTION[7]));
			break;
		case 7:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4] + EXTENDED_DESCRIPTION[5] + EXTENDED_DESCRIPTION[6] + EXTENDED_DESCRIPTION[7] + EXTENDED_DESCRIPTION[8]));
			break;
		case 8:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4] + EXTENDED_DESCRIPTION[5] + EXTENDED_DESCRIPTION[6] + EXTENDED_DESCRIPTION[7] + EXTENDED_DESCRIPTION[8] + EXTENDED_DESCRIPTION[9]));
			break;
		case 9:
			this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4] + EXTENDED_DESCRIPTION[5] + EXTENDED_DESCRIPTION[6] + EXTENDED_DESCRIPTION[7] + EXTENDED_DESCRIPTION[8] + EXTENDED_DESCRIPTION[9] + EXTENDED_DESCRIPTION[10]));
			break;
		default:
			break;
		}
		initializeTitle();
	}
    
	@Override
    public boolean canUpgrade() {
		if(this.timesUpgraded <= 8 && !(AbstractDungeon.getCurrRoom() instanceof RestRoom) && !(AbstractDungeon.getCurrRoom() instanceof EventRoom)) {
			return true;
		}
		else {
			return false;
		}
    }
	
    static {
    	cardStrings = CardCrawlGame.languagePack.getCardStrings("FormRideFlame");
        NAME = FormRideFlame.cardStrings.NAME;
        DESCRIPTION = FormRideFlame.cardStrings.DESCRIPTION;        
        EXTENDED_DESCRIPTION = FormRideFlame.cardStrings.EXTENDED_DESCRIPTION;
    }

	@Override
	public void energychange() {
		if(this.timesUpgraded >= 9) {
			this.timesUpgraded = 9;
			initializeTitle();
		}
		if(this.freeToPlayOnce){
			setBannerTexture(DCDmod.COMMON[0], DCDmod.COMMON_P[0]);
		}
		else if(this.costForTurn == -1 || this.costForTurn > 5) {
			setBannerTexture(DCDmod.COMMON[6], DCDmod.COMMON_P[6]);
		}
		else {
			int cost = this.costForTurn;
			setBannerTexture(DCDmod.COMMON[cost], DCDmod.COMMON_P[cost]);
		}
	}

	@Override
	public void onLoad(int[] arg0) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public int[] onSave() {
		// TODO 自动生成的方法存根
		return null;
	}

	
	
}
