package me.kiyotsuki.mirai.plugin;

import kotlin.Lazy;
import kotlin.LazyKt;
import net.mamoe.mirai.console.permission.*;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.User;

public final class Main extends JavaPlugin {
    public static final Main INSTANCE = new Main();

    private Main() {
        super(new JvmPluginDescriptionBuilder(
                "me.kiyotsuki.mirai.plugin.mirai-console-plugin-template-java",
                "0.0.1")
                .name("mirai-console-plugin-template-java")
                .author("Kiyotsuki")
                .info("一个简单的 Java 插件模板。")
                .build()
        );
    }

    @Override
    public void onEnable() {
        getLogger().info("注册权限...");
        PERM_ADMIN.getValue();
        getLogger().info("Mirai-Console-Plugin-Template-Java 插件已启用!");
    }

    public static final Lazy<Permission> PERM_ADMIN = LazyKt.lazy(() -> {
        try {
            return PermissionService.getInstance().register(
                    INSTANCE.permissionId("admin"),
                    "管理员权限",
                    INSTANCE.getParentPermission()
            );
        } catch (PermissionRegistryConflictException e) {
            throw new RuntimeException(e);
        }
    });

    public static boolean hasPermission(User user, Lazy<Permission> perm) {
        PermitteeId pid;
        if (user instanceof Member) {
            pid = new AbstractPermitteeId.ExactMember(((Member) user).getGroup().getId(), user.getId());
        } else {
            pid = new AbstractPermitteeId.ExactUser(user.getId());
        }
        return PermissionService.hasPermission(pid, perm.getValue());
    }
}
