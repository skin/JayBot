package it.nands.jaybot.foo;


import java.io.*;
import net.sf.jml.*;
import net.sf.jml.event.*;
import net.sf.jml.impl.*;
import net.sf.jml.message.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Damian Minkov
 * @version 1.0
 */
public class TestJml
{
    String FILE_TO_SEND = "c:/logz";


    static BasicMessenger messenger = null;
    ContactLisChangeListener contactLisChangeListener = new ContactLisChangeListener();
    boolean isListReady = false;

    public TestJml(String email, String password)
    {
        messenger = (BasicMessenger)MsnMessengerFactory.createMsnMessenger(
            email,
            password);
        messenger.setSupportedProtocol(new MsnProtocol[]{MsnProtocol.MSNP15});
        messenger.addContactListListener(contactLisChangeListener);

        messenger.addMessengerListener(new MsnMessengerListener()
        {
            public void loginCompleted(MsnMessenger messenger)
            {
            }

            public void logout(MsnMessenger messenger)
            {
            }

            public void exceptionCaught(MsnMessenger messenger,
                                        Throwable throwable)
            {
                throwable.printStackTrace();
            }
        });

        messenger.addMessageListener(new MsnMessageAdapter()
        {
            public void instantMessageReceived(MsnSwitchboard switchboard,
                                               MsnInstantMessage message,
                                               MsnContact contact)
            {
                System.out.println("ha msg received " + contact + " " + message + "/" + message.getContent());

                MsnFileTransfer ft =
                    messenger.getFileTransferManager().sendFile(
                    contact.getEmail(),
                    new File(FILE_TO_SEND));

                if(message.getContent().equals("stop"))
                    stop();
            }
        });

        messenger.addFileTransferListener(new MsnFileTransferListener() {

            public void fileTransferRequestReceived(MsnFileTransfer transfer)
            {
                System.out.println("ft req received " + transfer);

                transfer.setFile(new File("/tmp", transfer.getFile().getName()));
                transfer.start();
            }

            public void fileTransferStarted(MsnFileTransfer transfer)
            {
                System.out.println("ft started " + transfer);
            }

            public void fileTransferProcess(MsnFileTransfer transfer)
            {
                System.out.println("ft process " + transfer + " : " + transfer.getTransferredSize());
            }

            public void fileTransferFinished(MsnFileTransfer transfer)
            {
                System.out.println("ft end " + transfer + " state: " + transfer.getState().toString());
            }
        });
    }

    public void start()
    {
        messenger.login();
    }

    public void stop()
    {
        messenger.logout();
    }

    private class ContactLisChangeListener
        extends MsnContactListAdapter
    {
        public void contactListInitCompleted(MsnMessenger messenger)
        {
            System.out.println("contactListInitCompleted");
            synchronized(contactLisChangeListener){
                isListReady = true;
                contactLisChangeListener.notifyAll();
            }
        }
    }

    private void waitListComplete()
    {
        if(isListReady)
            return;

        synchronized(contactLisChangeListener)
        {
            try
            {
                contactLisChangeListener.wait();
            }
            catch(InterruptedException ex)
            {
            }
        }
    }

    public void printList()
    {
        waitListComplete();

        System.out.println("---=Start Printing contact list=---");

        MsnContactList list = messenger.getContactList();

        System.out.println("Forward list");
        MsnContact[] c = list.getContactsInList(MsnList.FL);
        for(int i = 0; i < c.length; i++)
        {
            System.out.println("c : " + c[i]);
            MsnGroup[] groups = c[i].getBelongGroups();
            for(int j = 0; j < groups.length; j++)
            {
                System.out.println("in group " + groups[j]);
            }
        }

        System.out.println("Allow list");
        c = list.getContactsInList(MsnList.AL);
        for(int i = 0; i < c.length; i++)
        {
            System.out.println("c : " + c[i] + " g:" + c[i].getBelongGroups().length);
        }

        System.out.println("Block list");
        c = list.getContactsInList(MsnList.BL);
        for(int i = 0; i < c.length; i++)
        {
            System.out.println("c : " + c[i] + " g:" + c[i].getBelongGroups().length);
        }

        System.out.println("pending list");
        c = list.getContactsInList(MsnList.PL);
        for(int i = 0; i < c.length; i++)
        {
            System.out.println("c : " + c[i] + " g:" + c[i].getBelongGroups().length);
        }

        System.out.println("Reverse list");
        c = list.getContactsInList(MsnList.RL);
        for(int i = 0; i < c.length; i++)
        {
            System.out.println("c : " + c[i] + " g:" +
                               c[i].getBelongGroups().length);
        }

        System.out.println("Number of groups : " + messenger.getContactList().getGroups().length);
        MsnGroup[] groups = messenger.getContactList().getGroups();
        for(int j = 0; j < groups.length; j++)
        {
            System.out.println("group " + groups[j]);
        }
        System.out.println("---=End Printing contact list=---");
    }

    public static void waitFor(long l)
    {
        Object lock = new Object();
        synchronized(lock)
        {
            try
            {
                lock.wait(l);
            }
            catch(InterruptedException ex)
            {
            }
        }

    }


    public static void main(String[] args)
    {
        if(args.length != 2)
        {
            System.out.println("Usage: username password");
            System.exit(-1);
        }

        TestJml testJml = new TestJml(args[0], args[1]);

        testJml.start();
        testJml.waitFor(5000);
        testJml.printList();
    }
}

