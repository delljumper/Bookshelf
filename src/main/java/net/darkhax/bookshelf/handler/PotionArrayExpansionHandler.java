package net.darkhax.bookshelf.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.darkhax.bookshelf.util.Constants;
import net.minecraft.potion.Potion;

public class PotionArrayExpansionHandler {

    Field potionTypes = null;
    Field modifiers = null;
    
    public PotionArrayExpansionHandler() {
        
        if (Potion.potionTypes.length < 4096) {
            
            Constants.LOG.info("Attempting to expand Potion Array to 256 spaces.");
            
            try {
                
                potionTypes = ReflectionHelper.findField(Potion.class, "a", "field_76425_a", "potionTypes");
                modifiers = Field.class.getDeclaredField("modifiers");
                
                if (potionTypes != null) {
                    
                    modifiers.setAccessible(true);
                    modifiers.setInt(potionTypes, potionTypes.getModifiers() & ~Modifier.FINAL);
                    
                    Potion[] existing = (Potion[]) potionTypes.get(null);
                    Potion[] expanded = new Potion[256];
                    System.arraycopy(existing, 0, expanded, 0, existing.length);
                    potionTypes.set(existing, expanded);
                    Constants.LOG.info("The Potion array has successfully been expanded to 256 spaces.");
                    return;
                }
            }
            
            catch (Exception e) {
                
                Constants.LOG.info("An exception occured during the expansion process. Please report this issue to the Bookshelf team! Make sure to provide a copy of this log!");
                e.printStackTrace();
            }
            
            Constants.LOG.info("The Potion array was not expanded.");
        }
    }
}