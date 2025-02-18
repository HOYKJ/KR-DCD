package dcdmod.Characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import basemod.abstracts.CustomPlayer;
import dcdmod.DCDmod;
import dcdmod.Actions.DecadeAnimationAction;
import dcdmod.Actions.TurnTimer;
import dcdmod.Card.Common.KamenRideDecade;
import dcdmod.Card.Rare.TimeVent;
import dcdmod.Helper.SpecialFaizButton;
import dcdmod.Helper.SpecialHibikiTaiko;
import dcdmod.Helper.SpecialHibikiTaikoScore;
import dcdmod.Helper.SpecialRideBooker;
import dcdmod.Patches.*;
import java.util.ArrayList;

public class Decade extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 3;
	public static final String[] orbTextures = { "img/character/orb/enabled/layer1.png", "img/character/orb/enabled/layer2.png", "img/character/orb/enabled/layer3.png", "img/character/orb/enabled/layer4.png", "img/character/orb/enabled/layer5.png", "img/character/orb/enabled/layer6.png", "img/character/orb/disabled/layer1d.png", "img/character/orb/disabled/layer2d.png", "img/character/orb/disabled/layer3d.png", "img/character/orb/disabled/layer4d.png", "img/character/orb/disabled/layer5d.png" };
	public static int cf;
	public static String mx;
	public static boolean haskamenpower = false;
	
	//人物模型
	public static String FAR_ATLAS = "img/char/DCD_Animational/FAR/FAR.atlas";
    public static String FAR_JSON0 = "img/char/DCD_Animational/FAR/FAR_FAR0.json";
    public static String FAR_JSON1 = "img/char/DCD_Animational/FAR/FAR_FAR1.json";
    public static String FAR_JSON2 = "img/char/DCD_Animational/FAR/FAR_FAR2.json";
    public static AnimationLoader FAR0 = new AnimationLoader(FAR_ATLAS, FAR_JSON0,  1.0f);
    public static AnimationLoader FAR1 = new AnimationLoader(FAR_ATLAS, FAR_JSON1,  1.0f);
    public static AnimationLoader FAR2 = new AnimationLoader(FAR_ATLAS, FAR_JSON2,  1.0f);
    public static AnimationLoader Animation = null;
    public static int [] ban = {13,15,19,20,21,22,24,25,26,27,31,32,33,36,38,42,51,52,53,56,57,58};

	
	public Decade(String name) {
		
		super(name, CharacterEnum.Decade, orbTextures, "img/character/orb/vfx.png", (String)null, (String)null);
		Decade.cf = 0;
		this.dialogX = (this.drawX + 0.0F * Settings.scale);
		this.dialogY = (this.drawY + 220.0F * Settings.scale);

		initializeClass(GetCharacterPicture.CharacterPicture(), DCDmod.makePath(DCDmod.MAGES_SHOULDER_2),
				DCDmod.makePath(DCDmod.MAGES_SHOULDER_1),
				DCDmod.makePath(DCDmod.MAGES_CORPSE),
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));	
	}
	
	public void update() {
		super.update();
		//时间降临
		if(AbstractDungeon.getCurrRoom() instanceof NeowRoom) {
			ModBaseClassForSLExample.timevent = false;
		}
		
		if(((AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getCurrRoom() instanceof MonsterRoom) &&AbstractDungeon.player != null && !AbstractDungeon.player.isDead)) {
			for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
				if(c.cardID == "TimeVent" && c.upgraded != true && !TimeVent.TimeVentUpgraded) {
					AbstractDungeon.player.discardPile.removeCard(c);
					TimeVent.TimeVentUpgraded = true;
					break;
				}
			}
		
			//藏匿
			int x = 0;
			CardGroup group = new CardGroup(CardGroup.CardGroupType.CARD_POOL); 
			   group.group.addAll(AbstractDungeon.player.drawPile.group);
			   group.group.addAll(AbstractDungeon.player.discardPile.group);
			   group.group.addAll(AbstractDungeon.player.hand.group);
			   group.group.addAll(AbstractDungeon.player.exhaustPile.group);
			for(AbstractCard c : group.group) {
				if(c.cardID == "NMDAZYYGL") {
					x++;
				}
			}
			if(AbstractDungeon.player.drawPile.group.size()!=1) {
				AbstractCard rc = null;
				for(int i=0;i<x;i++){
					for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
						if(c.cardID == "NMDAZYYGL") {
							AbstractDungeon.player.drawPile.group.remove(c);
							rc = c;
							break;
						}
					}
					if(rc != null) {
						AbstractDungeon.player.drawPile.group.add(0, rc);
						rc = null;
					}
				}
			}
			
			//唯一
			int cards = 0;
			int cards2 = 0;
			for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
				if(c.cardID == "NMDAZYYGL") {
					cards++;
				}
			}
			if(cards>1) {
				for(int i = 0;i<(cards-1);i++) {
					for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
						if(c.cardID == "NMDAZYYGL") {
							AbstractDungeon.player.masterDeck.group.remove(c);
							break;
						}
					}
				}
			}
			for(AbstractCard c : AbstractDungeon.player.hand.group) {
				if(c.cardID == "NMDAZYYGL") {
					cards2++;
				}
			}
			for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
				if(c.cardID == "NMDAZYYGL") {
					cards2++;
				}
			}
			for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
				if(c.cardID == "NMDAZYYGL") {
					cards2++;
				}
			}
			for(AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
				if(c.cardID == "NMDAZYYGL") {
					cards2++;
				}
			}
			if (cards2 > 1) {
				AbstractCard rc = null;
				for(int i = 0;i<cards;i++) {		
					for(AbstractCard c : AbstractDungeon.player.hand.group) {
						if(c.cardID == "NMDAZYYGL") {
							AbstractDungeon.player.hand.group.remove(c);
							rc = c;
							break;
						}
					}
					for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
						if(c.cardID == "NMDAZYYGL") {
							AbstractDungeon.player.hand.group.remove(c);
							rc = c;
							break;
						}
					}
					for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
						if(c.cardID == "NMDAZYYGL") {
							AbstractDungeon.player.hand.group.remove(c);
							rc = c;
							break;
						}
					}
					for(AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
						if(c.cardID == "NMDAZYYGL") {
							AbstractDungeon.player.hand.group.remove(c);
							rc = c;
							break;
						}
					}
				}
				AbstractDungeon.player.drawPile.group.add(0, rc);
			}
		
		}
	}

	
	public void Trickster(final int a) {
	    boolean banAnimationa = false;
	    boolean loop = false;
	    System.out.println("模型" + Decade.cf);
        if (a == Decade.cf) {
            System.out.println("==a");
            return;
        }
        else if(cf == 3){
            System.out.println("==3");
            return;
        }
        Decade.cf = a;
    	if(DCDmod.AnimationTrigger) {
			for(int i=0;i<ban.length;i++) {
    			if(a == ban[i]) {
    				banAnimationa = true;
    				break;
    			}
    			else if(a != ban[i]) {
    				banAnimationa = false;
    			}
    		}
    	}
    	if(banAnimationa) {
            System.out.println("==ban");
            return;
    	}
    	else if(!banAnimationa) {
			String AnimationName = DecadeAnimationAction.NAME[a];
			Animation = new AnimationLoader(DecadeAnimationAction.ATLAS[a], DecadeAnimationAction.JSON[a],  0.8f);
			if(AnimationName == "normal" || AnimationName == "normal_p" || AnimationName == "normal_t" || AnimationName == "normal_tp"
					 || AnimationName == "Dragon" || AnimationName == "Pegasus" || AnimationName == "Titan") {
				loop = true;
			}
			else {
				loop = false;
			}
			AnimationLoader.loadAnimation(AbstractDungeon.player,Animation);
			this.state.setAnimation(0, AnimationName, loop);
			switch(a) {
	        case 2:
	            SpecialRideBooker.kamenpowerpoint = 1;
	            SpecialRideBooker.nodecade = false;
	            SpecialRideBooker.haskamenpower = true;
	        	break;
	        case 3:
	            SpecialRideBooker.nodecade = true;
	        	break;
	        case 5:
	        	SpecialRideBooker.nodecade = true;
	        	break;
	        case 6:
	            SpecialRideBooker.kamenpowerpoint = 1;
	            SpecialRideBooker.nodecade = false;
	            SpecialRideBooker.haskamenpower = true;
	        	break;
	        case 10:
	            SpecialRideBooker.kamenpowerpoint = 1;
	            SpecialRideBooker.nodecade = false;
	            SpecialRideBooker.haskamenpower = true;
	            TurnTimer.StopBGM();
	        	break;
	        case 11:
	            SpecialRideBooker.kamenpowerpoint = 1;
	            SpecialRideBooker.nodecade = false;
	            SpecialRideBooker.haskamenpower = true;
	        	break;
	        case 12:
	        	SpecialRideBooker.nodecade = true;
	        	break;
	        case 23:
	            SpecialRideBooker.kamenpowerpoint = 1;
	            SpecialRideBooker.nodecade = false;
	            SpecialRideBooker.haskamenpower = true;
	            TurnTimer.StopBGM();
	        	break;
	        case 28:
	        	SpecialRideBooker.kamenpowerpoint = 2;
	        	SpecialRideBooker.nodecade = true;
	        	SpecialRideBooker.haskamenpower = true;
	        	break;
	        case 35:
	        	SpecialRideBooker.nodecade = true;
	        	break;
	        case 37:
	        	TurnTimer.StopBGM();
	        	break;
	        case 43:
	        	SpecialRideBooker.nodecade = true;
	        	break;
	        case 45:
	            SpecialRideBooker.kamenpowerpoint = 2;
	            SpecialRideBooker.haskamenpower = true;
	            TurnTimer.StopBGM();
	            CardCrawlGame.sound.playA("blade_BGM1", 0.0f);
	        	break;
	        case 46:
	            SpecialRideBooker.kamenpowerpoint = 3;
	            SpecialRideBooker.haskamenpower = true;
	        	break;
	        case 47:
	        	TurnTimer.StopBGM();
	        	break;
	        case 49:
	        	SpecialRideBooker.nodecade = true;
	        	HibikiTaikoKeyEvent.TaikoTrigger = true;
	        	break;
	        }
		}	        
	}

	@Override
	public void applyEndOfTurnTriggers() {
		for (AbstractPower p : this.powers) {
			p.atEndOfTurn(true);
		}
		AbstractDungeon.actionManager.addToBottom(new ExhaustAllEtherealAction());
	}

	public ArrayList<String> getStartingDeck() {
		new SpecialRideBooker();
		new SpecialFaizButton();
		new SpecialHibikiTaiko();
		new SpecialHibikiTaikoScore();
		ArrayList<String> retVal = new ArrayList<String>();
		retVal.add("Decade_Attack");
		retVal.add("Decade_Attack");
		retVal.add("Decade_Attack");
		retVal.add("Decade_Attack");
		retVal.add("KamenRide");
		retVal.add("FinalAttackRide");
		retVal.add("Decade_Defend");
		retVal.add("Decade_Defend");
		retVal.add("Decade_Defend");
		retVal.add("Decade_Defend");
		//retVal.add("AttackRide");
		//retVal.add("Kabuto_ClockUp");
		//retVal.add("Blade_Mach");
		//retVal.add("Blade_Metal");
		//retVal.add("Hibiki_Attack1");
		//retVal.add("UnarmedAttack1");
		//retVal.add("UnarmedAttack1");
		//retVal.add("UnarmedAttack1");
		//retVal.add("FormRideFlame");
		//retVal.add("FormRideStorm");
		//retVal.add("FormRide");
		//retVal.add("Kuuga_Rising");
		return retVal;
	}

	public ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<String>();
		retVal.add("Decadriver");
		UnlockTracker.markRelicAsSeen("Decadriver");
		return retVal;
	}

	public CharSelectInfo getLoadout() {
        String title;
        String flavor;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "假面骑士";
            flavor = "还不谢谢帝骑哥？";
        }
        else if (Settings.language == Settings.GameLanguage.ZHT) {
            title = "幪面超人";
            flavor = "仲唔多谢帝骑哥？";
        }
        else {
            title = "Decade";
            flavor = "The Decade,from Masked Rider DCD.";
        }
		return new CharSelectInfo(title, flavor,
				70, 70, 0, 99, 5,
				(AbstractPlayer)this, getStartingRelics(), getStartingDeck(), false);
	}
	
	@Override
	public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_HEAVY", MathUtils.random(-0.2f, 0.2f));
        CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.SHORT, true);
	}
	
	@Override
	public int getAscensionMaxHPLoss() {	
		return 5;
	}
	
	@Override
	public CardColor getCardColor() {
		return AbstractCardEnum.DCD;
	}
	
	@Override
	public int getCardCount() {
		return 0;
	}
	
	@Override
	public Color getCardRenderColor() {
		return DCDmod.DCD;
	}

	@Override
	public Color getCardTrailColor() {
		return DCDmod.DCD;
	}


	@Override
	public String getCustomModeCharacterButtonSoundKey() {
		return "ATTACK_HEAVY";
	}


    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }
    
	@Override
	public AbstractPlayer newInstance() {
		return new Decade(this.name);
    }
	
	@Override
	public String getLocalizedCharacterName() {
        String title;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "Decade";
        }
        else if (Settings.language == Settings.GameLanguage.ZHT) {
            title = "Decade";
        }
        else {
            title = "Decade";
        }
		return title;
	}
	
	@Override
	public int getSeenCardCount() {
		return 0;
	}

	@Override
	public Color getSlashAttackColor() {
		return DCDmod.DCD;
	}

	@Override
	public AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[] { AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL };
	}

	@Override
	public String getSpireHeartText() {
		return "......";
	}

	@Override
	public AbstractCard getStartCardForEvent() {
		return (AbstractCard)new KamenRideDecade();
	}


	@Override
	public String getTitle(final AbstractPlayer.PlayerClass arg0) {
        String title;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "Decade";
        }
        else if (Settings.language == Settings.GameLanguage.ZHT) {
            title = "Decade";
        }
        else {
            title = "Decade";
        }
		return title;
	}


	@Override
	public String getVampireText() {
		title = "....";
		return title;
	}
	


}
