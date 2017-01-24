package omtteam.omlib.compatability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import omtteam.omlib.reference.Reference;

/**
 * Created by Keridos on 23/01/2015. This Class
 */
@SuppressWarnings("WeakerAccess")
public class ModCompatibility {
    public static boolean IC2Loaded = false;
    public static boolean TeslaLoaded = false;

    public static void checkForMods() {

        IC2Loaded = Loader.isModLoaded("IC2");
        fixIC2Loading();
        TeslaLoaded = Loader.isModLoaded("tesla");
    }

    public static void fixIC2Loading() {
       /* if (IC2Loaded) {
            try {
                Class.forName("ic2.api.energy.tile.IEnergySink", false, ClassLoader.getSystemClassLoader());
            } catch (ClassNotFoundException e) {
                IC2Loaded = false;
                Logger.getLogger("OMlib").severe("IC2 should be present but class not found!");
            }
        }  */
    }

    private static void addVersionCheckerInfo() {
        NBTTagCompound versionchecker = new NBTTagCompound();
        versionchecker.setString("curseProjectName", "224663-omtteam.openmodularturrets");
        versionchecker.setString("curseFilenameParser", "OMLib-1.10.2-[].jar");
        versionchecker.setString("modDisplayName", "OpenModularTurrets");
        versionchecker.setString("oldVersion", Reference.VERSION);
        FMLInterModComms.sendRuntimeMessage("omtteam/omlib", "VersionChecker", "addCurseCheck", versionchecker);
    }

    public static void performModCompat() {
        addVersionCheckerInfo();
    }

}
