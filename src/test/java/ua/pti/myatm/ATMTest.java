/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;

/**
 *
 * @author Olya
 */
public class ATMTest {

    @Test (expected=NegativeAmount.class)
    public void testGetMoneyInATMWithNegativeAmountEx() throws NegativeAmount{
        System.out.println("Get Money In ATM Where MoneyAmount is negative");
        double moneyInATM = -100;
        ATM instance = new ATM(moneyInATM);
        double expResult = -100;
        double result = instance.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }
    
    @Test
    public void testGetMoneyInATMWithZeroAmount() throws NegativeAmount {
        System.out.println("getMoneyInATM where moneyamount=0");
        double moneyInATM=0;
        ATM instance=new ATM(moneyInATM);
        double expResult = 0;
        double result = instance.getMoneyInATM();
        assertEquals(expResult, result, 0.0);    
    }
    
    @Test
    public void testGetMoneyInATM() throws NegativeAmount {
        System.out.println("getMoneyInATM where moneyamount=1000");
        double moneyInATM=1000;
        ATM instance=new ATM(moneyInATM);
        double expResult = 1000;
        double result = instance.getMoneyInATM();
        assertEquals(expResult, result, 0.0);    
    }
    
    @Test(expected = NoCardInserted.class)
    public void testValidateCardWithNocardInserted() throws NoCardInserted, NegativeAmount {
        System.out.println("Validate Card With No Card Initialized");
        Card card = null;;
        ATM instance = new ATM(0);
        boolean result = instance.validateCard(card,1234);
    }
    
    @Test
    public void testValidateCardUncorrectPIN() throws NoCardInserted, NegativeAmount {
        System.out.println("validateCard with uncorrect PIN");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1235)).thenReturn(false);
        when(mockedcard.isBlocked()).thenReturn(false);
        int pinCode = 1235;
        ATM instance = new ATM(0);
        boolean expResult = false;
        boolean result = instance.validateCard(mockedcard, pinCode);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateCardBlocked() throws NoCardInserted, NegativeAmount {
        System.out.println("validate blocked Card");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.isBlocked()).thenReturn(true);
        int pinCode = 1234;
        ATM instance = new ATM(1000);
        boolean expResult = false;
        boolean result = instance.validateCard(mockedcard, pinCode);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateCardWithTruePar() throws NoCardInserted ,NegativeAmount {
        System.out.println("validate unlocked Card with correct PIN");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.isBlocked()).thenReturn(false);
        int pinCode = 1234;
        ATM instance = new ATM(1000);
        boolean expResult = true;
        boolean result = instance.validateCard(mockedcard, pinCode);
        assertEquals(expResult, result);
    }
    
        @Test
    public void testValidateCardWithFalsePar() throws NoCardInserted, NegativeAmount {
        System.out.println("validate locked Card with uncorrect PIN");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(false);
        when(mockedcard.isBlocked()).thenReturn(true);
        int pinCode = 1234;
        ATM instance = new ATM(1000);
        boolean expResult = false;
        boolean result = instance.validateCard(mockedcard, pinCode);
        assertEquals(expResult, result);
    }
    
    @Test(expected = NoCardInserted.class)
    public void testCheckBalanceWithNocardInserted() throws NoCardInserted, NegativeAmount {
        System.out.println("Check Card Balance With No Card Initialized");
        ATM instance = new ATM(0);
        double result = instance.checkBalance();
    }
    
    @Test
    public void testCheckBalance() throws NegativeAmount, NoCardInserted {
        System.out.println("checkBalance with valid card");
        Card mockedcard = mock(Card.class);
        Account mockedcardacc = mock(Account.class);
        when(mockedcard.isBlocked()).thenReturn(false);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.getAccount()).thenReturn(mockedcardacc);
        when(mockedcardacc.getBalance()).thenReturn(1000.0);
        ATM instance = new ATM(0);
        instance.validateCard(mockedcard, 1234);
        double expResult = 1000.0;
        double result = instance.checkBalance();
        assertEquals(expResult, result, 0.0);
    }
    
    @Test(expected = NoCardInserted.class)
    public void testGetCashWithNocardInserted() throws NoCardInserted, NegativeAmount, NotEnoughMoneyInAccount, NotEnoughMoneyInATM {
        System.out.println("Check Card Balance With No Card Initialized");
        ATM instance = new ATM(0);
        double result = instance.getCash(100);
    }
    
    @Test(expected = NotEnoughMoneyInAccount.class)
    public void testGetCashWithNoEnoughMoneyInAccount() throws NoCardInserted, NegativeAmount, NotEnoughMoneyInAccount, NotEnoughMoneyInATM {
        System.out.println("Get Cash With No Enough Money In your Account");
        Card mockedcard = mock(Card.class);
        Account mockedcardacc = mock(Account.class);
        when(mockedcard.isBlocked()).thenReturn(false);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.getAccount()).thenReturn(mockedcardacc);
        when(mockedcardacc.getBalance()).thenReturn(1000.0);
        when(mockedcardacc.withdrow(1000.01)).thenReturn(1000.01);
        double amount = 1000.01;
        ATM instance = new ATM(2000);
        instance.validateCard(mockedcard,1234);
        double result = instance.getCash(amount);
    }
    
    @Test(expected = NoCardInserted.class)
    public void testGetCashWithNoCardInsertedAndEnoughMoneyInATM() throws NoCardInserted, NegativeAmount, NotEnoughMoneyInAccount, NotEnoughMoneyInATM {
        System.out.println("Get Cash With Enough Money but with no card insrted");
        ATM instance = new ATM(2000);
        double amount = 100;        
        double result = instance.getCash(amount);
    }
        

    @Test
    public void testGetCashWithEnoughMoneyBoth() throws NegativeAmount, NoCardInserted, NotEnoughMoneyInAccount, NotEnoughMoneyInATM {
        System.out.println("getCash when no enough money in ATM");
         Card mockedcard = mock(Card.class);
         Account mockedcardacc = mock(Account.class);
         when(mockedcard.isBlocked()).thenReturn(false);
         when(mockedcard.checkPin(1234)).thenReturn(true);
         when(mockedcard.getAccount()).thenReturn(mockedcardacc);
         when(mockedcardacc.getBalance()).thenReturn(1500.0);
         when(mockedcardacc.withdrow(100)).thenReturn(100.0);
        double amount = 100;
        ATM instance = new ATM(1000);
        instance.validateCard(mockedcard,1234);
        double expResult = 100;
        double result = instance.getCash(amount);
        assertEquals(expResult, result, 0.0);
    }
    
    @Test
    public void testGetCashInOrder() throws NegativeAmount, NotEnoughMoneyInATM, NoCardInserted, NotEnoughMoneyInAccount {
        System.out.println("Get Cash test with right order of methods");
        Card mockedcard = mock(Card.class);
        Account mockedcardacc = mock(Account.class);
        when(mockedcard.isBlocked()).thenReturn(false);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.getAccount()).thenReturn(mockedcardacc);
        when(mockedcardacc.getBalance()).thenReturn(1500.0);
        when(mockedcardacc.withdrow(100)).thenReturn(100.0);
        double amount = 100.0;
        ATM instance = new ATM(1000);
        boolean t = instance.validateCard(mockedcard,1234);
        double result = instance.getCash(amount);
        InOrder inOrder = inOrder(mockedcard, mockedcardacc);
        inOrder.verify(mockedcard).getAccount();
        inOrder.verify(mockedcardacc).getBalance();
        inOrder.verify(mockedcardacc).withdrow(100);
    }
    
    @Test
    public void testValidateCardAtLeastCheck() throws NoCardInserted, NegativeAmount{
        System.out.println("Validate Card for existing card PIN check");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(false);
        when(mockedcard.isBlocked()).thenReturn(false);
        int pinCode = 1234;
        ATM instance = new ATM(0);
        boolean result = instance.validateCard(mockedcard,pinCode);
        verify(mockedcard, atLeastOnce()).checkPin(pinCode);
    }
    
    
   @Test
   public void testValidateCard3Uncorrect() throws NegativeAmount, NoCardInserted{
           System.out.println("Card has been blocked, because of 3 times wrong PIN inserted");
           Card mockedcard = mock(Card.class);
           when(mockedcard.isBlocked()).thenReturn(false);
           when(mockedcard.checkPin(1234)).thenReturn(false);
           ATM instance = new ATM(0);
           boolean result = instance.validateCard(mockedcard,1234);
           result = instance.validateCard(mockedcard,1234);
           result = instance.validateCard(mockedcard,1234);
           verify(mockedcard, atLeastOnce()).block();
   }
   
   @Test
   public void testValidateCard2Uncorrect() throws NegativeAmount, NoCardInserted{
           System.out.println("Validate card 2 times uncorrect PIN");
           Card mockedcard = mock(Card.class);
           when(mockedcard.isBlocked()).thenReturn(false);
           when(mockedcard.checkPin(1234)).thenReturn(false);
           ATM instance = new ATM(0);
           boolean result = instance.validateCard(mockedcard,1234);
           result = instance.validateCard(mockedcard,1234);
           verify(mockedcard, never()).block();
   }
   
      @Test
   public void testValidateCard4Uncorrect() throws NegativeAmount, NoCardInserted{
           System.out.println("Card has been blocked, because of 4 times wrong PIN inserted");
           Card mockedcard = mock(Card.class);
           when(mockedcard.isBlocked()).thenReturn(false);
           when(mockedcard.checkPin(1234)).thenReturn(false);
           ATM instance = new ATM(0);
           boolean result = instance.validateCard(mockedcard,1234);
           result = instance.validateCard(mockedcard,1234);
           result = instance.validateCard(mockedcard,1234);
           result = instance.validateCard(mockedcard,1234);
           verify(mockedcard, atLeastOnce()).block();
   }
   
         @Test
   public void testValidateCard2UncorrectOtherCardUncorrect() throws NegativeAmount, NoCardInserted{
           System.out.println("Validate card with 2 uncorrect PIN inserted change card, and set uncorrect PIN twice again");
           Card mockedcard = mock(Card.class);
           Card newmockedcard = mock(Card.class);
           when(mockedcard.isBlocked()).thenReturn(false);
           when(mockedcard.checkPin(1234)).thenReturn(false);
           when(mockedcard.getID()).thenReturn(1);
           when(newmockedcard.isBlocked()).thenReturn(false);
           when(newmockedcard.checkPin(0000)).thenReturn(true);
           when(mockedcard.getID()).thenReturn(2);
           ATM instance = new ATM(0);
           boolean result = instance.validateCard(mockedcard,1234);
           result = instance.validateCard(mockedcard,1234);
           result = instance.validateCard(newmockedcard,0000);
           result = instance.validateCard(mockedcard,1234);
           result = instance.validateCard(mockedcard,1234);
           verify(mockedcard, never()).block();
           verify(newmockedcard, never()).block();
   }
     
         @Test
   public void testValidateCard2UncorrectOtherBlockedandUncorrectagain() throws NegativeAmount, NoCardInserted{
           System.out.println("Validate card with 2 uncorrect PIN inserted change card, blocked it, and set uncorrect PIN again");
           Card mockedcard = mock(Card.class);
           Card newmockedcard = mock(Card.class);
           when(mockedcard.isBlocked()).thenReturn(false);
           when(mockedcard.checkPin(1234)).thenReturn(false);
           when(mockedcard.getID()).thenReturn(1);
           when(newmockedcard.isBlocked()).thenReturn(false);
           when(newmockedcard.checkPin(0000)).thenReturn(false);
           when(mockedcard.getID()).thenReturn(2);
           ATM instance = new ATM(0);
           boolean result = instance.validateCard(mockedcard,1234);
           result = instance.validateCard(mockedcard,1234);
           result = instance.validateCard(newmockedcard,0000);
           result = instance.validateCard(newmockedcard,0000);
           result = instance.validateCard(newmockedcard,0000);
           result = instance.validateCard(mockedcard,1234);
           verify(mockedcard, never()).block();
           verify(newmockedcard, atLeastOnce()).block();
   }


}
