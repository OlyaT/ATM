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
    public void testGetCashWithNoCardInsertedAndEnoughMoney() throws NoCardInserted, NegativeAmount, NotEnoughMoneyInAccount, NotEnoughMoneyInATM {
        System.out.println("Get Cash With Enough Money but with no card insrted");
        Card mockedcard = mock(Card.class);
        Account mockedcardacc = mock(Account.class);
        when(mockedcard.isBlocked()).thenReturn(false);
        when(mockedcard.checkPin(1234)).thenReturn(false);
        when(mockedcard.getAccount()).thenReturn(mockedcardacc);
        when(mockedcardacc.getBalance()).thenReturn(1000.0);
        when(mockedcardacc.withdrow(100)).thenReturn(100.0);
        double amount = 100;
        ATM instance = new ATM(2000);
        instance.validateCard(mockedcard,1234);
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


}
