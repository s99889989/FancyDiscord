package com.daxton.fancydiscord.command;

import com.daxton.fancydiscord.FancyDiscord;
import com.daxton.fancydiscord.config.FileConfig;
import com.daxton.fancydiscord.task.server.ReStart;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandMain {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("fancydiscord")
			.then(Commands.literal("reload")
				.requires(commandSource -> commandSource.hasPermission(4))
				.executes(context -> {
					ReStart.execute();
					String reloadString = FileConfig.languageConfig.getString("reload_message");
					FancyDiscord.sendLogger(reloadString);
					return Command.SINGLE_SUCCESS;
				})
			)

		);

	}

}
