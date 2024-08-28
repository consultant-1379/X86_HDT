SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `HDT_SCHEMA` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`valid_product` (
  `product_weight` INT(11) NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`product_weight`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`product_release` (
  `version_name` VARCHAR(40) NOT NULL,
  `bundle_id` INT(11) NOT NULL,
  `combined_network_weight` INT(11) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  `GA` TINYINT(1) NOT NULL DEFAULT false,
  `formula_name` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`version_name`, `bundle_id`, `combined_network_weight`, `product_weight`),
  INDEX `fk_product_release_bundle1_idx` (`bundle_id` ASC),
  INDEX `fk_product_release_combined_network1_idx` (`combined_network_weight` ASC),
  INDEX `fk_product_release_formula1_idx` (`formula_name` ASC),
  INDEX `fk_product_release_version1_idx` (`version_name` ASC),
  INDEX `fk_product_release_valid_product1_idx` (`product_weight` ASC),
  CONSTRAINT `fk_product_release_bundle1`
    FOREIGN KEY (`bundle_id`)
    REFERENCES `HDT_SCHEMA`.`bundle` (`id`)
    ON DELETE NO ACTION
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_product_release_combined_network1`
    FOREIGN KEY (`combined_network_weight`)
    REFERENCES `HDT_SCHEMA`.`combined_network` (`weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_release_formula1`
    FOREIGN KEY (`formula_name`)
    REFERENCES `HDT_SCHEMA`.`formula` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_release_version1`
    FOREIGN KEY (`version_name`)
    REFERENCES `HDT_SCHEMA`.`version` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_release_valid_product1`
    FOREIGN KEY (`product_weight`)
    REFERENCES `HDT_SCHEMA`.`valid_product` (`product_weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`product_release_role` (
  `roles_id` INT(11) NOT NULL,
  `site_id` INT(11) NOT NULL,
  `dependency` TINYINT(1) NULL DEFAULT NULL,
  `mandatory` TINYINT(1) NULL DEFAULT NULL,
  `info_note_id` INT(11) NULL DEFAULT NULL,
  `visible` TINYINT(1) NULL DEFAULT NULL,
  `result_variable` VARCHAR(40) NOT NULL,
  `formula_name` VARCHAR(200) NOT NULL,
  `note_visible` TINYINT(1) NULL DEFAULT NULL,
  `version_name` VARCHAR(40) NOT NULL,
  `bundle_id` INT(11) NOT NULL,
  `combined_network_weight` INT(11) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  `display_order` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`roles_id`, `site_id`, `version_name`, `bundle_id`, `combined_network_weight`, `product_weight`),
  INDEX `fk_product_release_role_roles1_idx` (`roles_id` ASC),
  INDEX `fk_product_release_role_info_note1_idx` (`info_note_id` ASC),
  INDEX `fk_product_release_role_formula1_idx` (`formula_name` ASC),
  INDEX `fk_product_release_role_product_release1_idx` (`version_name` ASC, `bundle_id` ASC, `combined_network_weight` ASC, `product_weight` ASC),
  CONSTRAINT `fk_product_release_role_roles1`
    FOREIGN KEY (`roles_id`)
    REFERENCES `HDT_SCHEMA`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_release_role_info_note1`
    FOREIGN KEY (`info_note_id`)
    REFERENCES `HDT_SCHEMA`.`info_note` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_release_role_formula1`
    FOREIGN KEY (`formula_name`)
    REFERENCES `HDT_SCHEMA`.`formula` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_release_role_product_release1`
    FOREIGN KEY (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    REFERENCES `HDT_SCHEMA`.`product_release` (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`app_number` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  `app_type` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_app_number_app_type1_idx` (`app_type` ASC),
  CONSTRAINT `fk_app_number_app_type1`
    FOREIGN KEY (`app_type`)
    REFERENCES `HDT_SCHEMA`.`app_type` (`type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`product_release_notes` (
  `info_note_id` INT(11) NOT NULL,
  `visible` TINYINT(1) NULL DEFAULT NULL,
  `version_name` VARCHAR(40) NOT NULL,
  `bundle_id` INT(11) NOT NULL,
  `combined_network_weight` INT(11) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  INDEX `fk_product_release_notes_product_release1_idx` (`version_name` ASC, `bundle_id` ASC, `combined_network_weight` ASC, `product_weight` ASC),
  PRIMARY KEY (`info_note_id`, `version_name`, `bundle_id`, `combined_network_weight`, `product_weight`),
  CONSTRAINT `fk_product_release_notes_product_release1`
    FOREIGN KEY (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    REFERENCES `HDT_SCHEMA`.`product_release` (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_release_notes_info_note1`
    FOREIGN KEY (`info_note_id`)
    REFERENCES `HDT_SCHEMA`.`info_note` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`url_link` (
  `url` VARCHAR(200) NOT NULL,
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `desc` VARCHAR(60) NULL DEFAULT NULL,
  `default_link` TINYINT(1) NOT NULL DEFAULT false,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`product_parameter` (
  `value` FLOAT(11) NULL DEFAULT NULL,
  `parameter_id` INT(11) NOT NULL,
  `sub_par` TINYINT(1) NULL DEFAULT NULL,
  `enabled` TINYINT(1) NULL DEFAULT NULL,
  `visible_state` INT(11) NULL DEFAULT NULL,
  `version_name` VARCHAR(40) NOT NULL,
  `bundle_id` INT(11) NOT NULL,
  `combined_network_weight` INT(11) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  `display_order` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`parameter_id`, `version_name`, `bundle_id`, `combined_network_weight`, `product_weight`),
  INDEX `fk_product_parameter_parameter1_idx` (`parameter_id` ASC),
  INDEX `fk_product_parameter_product_release1_idx` (`version_name` ASC, `bundle_id` ASC, `combined_network_weight` ASC, `product_weight` ASC),
  CONSTRAINT `fk_product_parameter_parameter1`
    FOREIGN KEY (`parameter_id`)
    REFERENCES `HDT_SCHEMA`.`parameter` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_parameter_product_release1`
    FOREIGN KEY (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    REFERENCES `HDT_SCHEMA`.`product_release` (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`rack` (
  `id` INT(11) NOT NULL,
  `component_name` VARCHAR(20) NOT NULL,
  `start_pos` INT(11) NOT NULL,
  `version_name` VARCHAR(40) NOT NULL,
  `bundle_id` INT(11) NOT NULL,
  `combined_network_weight` INT(11) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  `site_id` INT(11) NOT NULL,
  INDEX `fk_rack_component1_idx` (`component_name` ASC),
  PRIMARY KEY (`version_name`, `bundle_id`, `combined_network_weight`, `product_weight`, `id`, `component_name`, `start_pos`, `site_id`),
  CONSTRAINT `fk_rack_component1`
    FOREIGN KEY (`component_name`)
    REFERENCES `HDT_SCHEMA`.`component` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rack_product_release1`
    FOREIGN KEY (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    REFERENCES `HDT_SCHEMA`.`product_release` (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`component_type` (
  `type` VARCHAR(20) NOT NULL,
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`component` (
  `name` VARCHAR(20) NOT NULL,
  `rack_unit` INT(11) NULL DEFAULT NULL,
  `dependency` TINYINT(1) NULL DEFAULT NULL,
  `component_type_id` INT(11) NOT NULL,
  `app_number_id` INT(11) NOT NULL,
  PRIMARY KEY (`name`, `app_number_id`),
  INDEX `fk_component_componet_type1_idx` (`component_type_id` ASC),
  INDEX `fk_component_app_number1_idx` (`app_number_id` ASC),
  CONSTRAINT `fk_component_componet_type1`
    FOREIGN KEY (`component_type_id`)
    REFERENCES `HDT_SCHEMA`.`component_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_component_app_number1`
    FOREIGN KEY (`app_number_id`)
    REFERENCES `HDT_SCHEMA`.`app_number` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`dependency` (
  `dep_name` VARCHAR(20) NULL DEFAULT NULL,
  `dep_app` VARCHAR(20) NULL DEFAULT NULL,
  `component_name` VARCHAR(20) NOT NULL,
  `component_app_number_id` INT(11) NOT NULL,
  PRIMARY KEY (`component_name`, `component_app_number_id`),
  CONSTRAINT `fk_dependency_component1`
    FOREIGN KEY (`component_name` , `component_app_number_id`)
    REFERENCES `HDT_SCHEMA`.`component` (`name` , `app_number_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`bundle` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`hw_config_bundle` (
  `qty` INT(11) NOT NULL,
  `app_number_id` INT(11) NOT NULL,
  `hw_config_id` INT(11) NOT NULL,
  PRIMARY KEY (`app_number_id`, `hw_config_id`),
  INDEX `fk_hw_config_app_number1_idx` (`app_number_id` ASC),
  INDEX `fk_hw_config_bundle_hw_config1_idx` (`hw_config_id` ASC),
  CONSTRAINT `fk_hw_config_app_number1`
    FOREIGN KEY (`app_number_id`)
    REFERENCES `HDT_SCHEMA`.`app_number` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hw_config_bundle_hw_config1`
    FOREIGN KEY (`hw_config_id`)
    REFERENCES `HDT_SCHEMA`.`hw_config` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`combined_network` (
  `weight` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`weight`, `name`),
  INDEX `fk_combined_network_networks1_idx` (`name` ASC),
  CONSTRAINT `fk_combined_network_networks1`
    FOREIGN KEY (`name`)
    REFERENCES `HDT_SCHEMA`.`networks` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`networks` (
  `name` VARCHAR(45) NOT NULL,
  `weight` INT(11) NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`product` (
  `name` VARCHAR(45) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  `GA` TINYINT(1) NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`formula` (
  `name` VARCHAR(200) NOT NULL,
  `fml_code` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`info_note` (
  `content` VARCHAR(400) NULL DEFAULT NULL,
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`role_dependency` (
  `dep_roles_id` INT(11) NOT NULL,
  `roles_id` INT(11) NOT NULL,
  `site_id` INT(11) NOT NULL,
  `version_name` VARCHAR(40) NOT NULL,
  `bundle_id` INT(11) NOT NULL,
  `combined_network_weight` INT(11) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  PRIMARY KEY (`dep_roles_id`, `roles_id`, `site_id`, `version_name`, `bundle_id`, `combined_network_weight`, `product_weight`),
  INDEX `fk_role_dependency_product_release_role1_idx` (`roles_id` ASC, `site_id` ASC, `version_name` ASC, `bundle_id` ASC, `combined_network_weight` ASC, `product_weight` ASC),
  CONSTRAINT `fk_role_dependency_roles1`
    FOREIGN KEY (`dep_roles_id`)
    REFERENCES `HDT_SCHEMA`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_dependency_product_release_role1`
    FOREIGN KEY (`roles_id` , `site_id` , `version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    REFERENCES `HDT_SCHEMA`.`product_release_role` (`roles_id` , `site_id` , `version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`sub_parameter` (
  `value` FLOAT(11) NULL DEFAULT NULL,
  `sub_parameter_id` INT(11) NOT NULL,
  `enabled` TINYINT(1) NULL DEFAULT NULL,
  `visible_state` INT(11) NULL DEFAULT NULL,
  `main_parameter_id` INT(11) NOT NULL,
  `version_name` VARCHAR(40) NOT NULL,
  `bundle_id` INT(11) NOT NULL,
  `combined_network_weight` INT(11) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  PRIMARY KEY (`sub_parameter_id`, `main_parameter_id`, `version_name`, `bundle_id`, `combined_network_weight`, `product_weight`),
  INDEX `fk_sub_parameter_product_parameter1_idx` (`main_parameter_id` ASC, `version_name` ASC, `bundle_id` ASC, `combined_network_weight` ASC, `product_weight` ASC),
  CONSTRAINT `fk_sub_parameter_parameter1`
    FOREIGN KEY (`sub_parameter_id`)
    REFERENCES `HDT_SCHEMA`.`parameter` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sub_parameter_product_parameter1`
    FOREIGN KEY (`main_parameter_id` , `version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    REFERENCES `HDT_SCHEMA`.`product_parameter` (`parameter_id` , `version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`parameter` (
  `id` INT(11) NULL DEFAULT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NULL DEFAULT NULL,
  `parameter_type_id` INT(11) NOT NULL,
  `desc` VARCHAR(100) NOT NULL,
  `system` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_parameter_parameter_type1_idx` (`parameter_type_id` ASC),
  CONSTRAINT `fk_parameter_parameter_type1`
    FOREIGN KEY (`parameter_type_id`)
    REFERENCES `HDT_SCHEMA`.`parameter_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`roles` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `desc` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`hwconfig_change_per_role` (
  `hwconfig_change_id` INT(11) NOT NULL AUTO_INCREMENT,
  `date_change` DATE NOT NULL,
  `hw_config_id` INT(11) NOT NULL,
  PRIMARY KEY (`hwconfig_change_id`, `hw_config_id`),
  INDEX `fk_hwconfig_change_per_role_hwconfig_change1_idx` (`hwconfig_change_id` ASC),
  INDEX `fk_hwconfig_change_per_role_hw_config1_idx` (`hw_config_id` ASC),
  CONSTRAINT `fk_hwconfig_change_per_role_hwconfig_change1`
    FOREIGN KEY (`hwconfig_change_id`)
    REFERENCES `HDT_SCHEMA`.`hwconfig_change` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hwconfig_change_per_role_hw_config1`
    FOREIGN KEY (`hw_config_id`)
    REFERENCES `HDT_SCHEMA`.`hw_config` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`hwconfig_change` (
  `id` INT(11) NOT NULL,
  `reason` VARCHAR(250) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`app_type` (
  `type` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`type`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`role_hw_config` (
  `result_variable` DOUBLE NOT NULL,
  `hw_ver` INT(11) NOT NULL,
  `hw_config_id` INT(11) NOT NULL,
  `info_note_id` INT(11) NULL DEFAULT NULL,
  `note_visible` TINYINT(1) NULL DEFAULT NULL,
  `roles_id` INT(11) NOT NULL,
  `site_id` INT(11) NOT NULL,
  `version_name` VARCHAR(40) NOT NULL,
  `bundle_id` INT(11) NOT NULL,
  `combined_network_weight` INT(11) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  PRIMARY KEY (`result_variable`, `hw_ver`, `hw_config_id`, `roles_id`, `site_id`, `version_name`, `bundle_id`, `combined_network_weight`, `product_weight`),
  INDEX `fk_role_hw_config_hw_config1_idx` (`hw_config_id` ASC),
  INDEX `fk_role_hw_config_info_note1_idx` (`info_note_id` ASC),
  INDEX `fk_role_hw_config_product_release_role1_idx` (`roles_id` ASC, `site_id` ASC, `version_name` ASC, `bundle_id` ASC, `combined_network_weight` ASC, `product_weight` ASC),
  CONSTRAINT `fk_role_hw_config_hw_config1`
    FOREIGN KEY (`hw_config_id`)
    REFERENCES `HDT_SCHEMA`.`hw_config` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_hw_config_info_note1`
    FOREIGN KEY (`info_note_id`)
    REFERENCES `HDT_SCHEMA`.`info_note` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_hw_config_product_release_role1`
    FOREIGN KEY (`roles_id` , `site_id` , `version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    REFERENCES `HDT_SCHEMA`.`product_release_role` (`roles_id` , `site_id` , `version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`hw_config` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `desc` VARCHAR(145) NULL DEFAULT NULL,
  `eol_date` VARCHAR(12) NULL DEFAULT NULL,
  `eol` TINYINT(4) NULL DEFAULT NULL,
  `app_type_type` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_hw_config_app_type1_idx` (`app_type_type` ASC),
  CONSTRAINT `fk_hw_config_app_type1`
    FOREIGN KEY (`app_type_type`)
    REFERENCES `HDT_SCHEMA`.`app_type` (`type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`product_url_link` (
  `url_link_id` INT(11) NOT NULL,
  `visible` TINYINT(1) NOT NULL,
  `help_menu_text` VARCHAR(60) NOT NULL,
  `version_name` VARCHAR(40) NOT NULL,
  `bundle_id` INT(11) NOT NULL,
  `combined_network_weight` INT(11) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  PRIMARY KEY (`url_link_id`, `version_name`, `bundle_id`, `combined_network_weight`, `product_weight`),
  INDEX `fk_product_url_link_product_release1_idx` (`version_name` ASC, `bundle_id` ASC, `combined_network_weight` ASC, `product_weight` ASC),
  CONSTRAINT `fk_product_url_link_url_link1`
    FOREIGN KEY (`url_link_id`)
    REFERENCES `HDT_SCHEMA`.`url_link` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_url_link_product_release1`
    FOREIGN KEY (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    REFERENCES `HDT_SCHEMA`.`product_release` (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`role_hw_change` (
  `date_changed` DATE NOT NULL,
  `hw_config_id_new` INT(11) NOT NULL,
  `hw_config_id_old` INT(11) NULL DEFAULT NULL,
  `roles_id` INT(11) NOT NULL,
  `role_site_id` INT(11) NOT NULL,
  `version_name` VARCHAR(40) NOT NULL,
  `bundle_id` INT(11) NOT NULL,
  `combined_network_weight` INT(11) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  INDEX `fk_role_hw_change_hw_config1_idx` (`hw_config_id_new` ASC),
  INDEX `fk_role_hw_change_hw_config2_idx` (`hw_config_id_old` ASC),
  INDEX `fk_role_hw_change_product_release_role1_idx` (`roles_id` ASC, `role_site_id` ASC, `version_name` ASC, `bundle_id` ASC, `combined_network_weight` ASC, `product_weight` ASC),
  CONSTRAINT `fk_role_hw_change_hw_config1`
    FOREIGN KEY (`hw_config_id_new`)
    REFERENCES `HDT_SCHEMA`.`hw_config` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_hw_change_hw_config2`
    FOREIGN KEY (`hw_config_id_old`)
    REFERENCES `HDT_SCHEMA`.`hw_config` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_hw_change_product_release_role1`
    FOREIGN KEY (`roles_id` , `role_site_id` , `version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    REFERENCES `HDT_SCHEMA`.`product_release_role` (`roles_id` , `site_id` , `version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`network_limits` (
  `network_limits` TEXT NULL DEFAULT NULL,
  `product_release_product_name` VARCHAR(45) NOT NULL,
  `version_name` VARCHAR(40) NOT NULL,
  `bundle_id` INT(11) NOT NULL,
  `combined_network_weight` INT(11) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  INDEX `fk_network_limits_product_release1_idx` (`version_name` ASC, `bundle_id` ASC, `combined_network_weight` ASC, `product_weight` ASC),
  CONSTRAINT `fk_network_limits_product_release1`
    FOREIGN KEY (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    REFERENCES `HDT_SCHEMA`.`product_release` (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`parameter_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`version` (
  `name` VARCHAR(40) NOT NULL,
  `desc` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`user` (
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(200) NOT NULL,
  `firstname` VARCHAR(45) NULL DEFAULT NULL,
  `lastname` VARCHAR(45) NULL DEFAULT NULL,
  `role` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`passwd` (
  `passwd` VARCHAR(80) NULL DEFAULT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `fk_passwd_user1`
    FOREIGN KEY (`email`)
    REFERENCES `HDT_SCHEMA`.`user` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`user_login` (
  `login_time` TIMESTAMP NULL DEFAULT NULL,
  `logout_time` TIMESTAMP NULL DEFAULT NULL,
  `logged_in_now` TINYINT(4) NULL DEFAULT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `fk_user_login_user1`
    FOREIGN KEY (`email`)
    REFERENCES `HDT_SCHEMA`.`user` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`user_stored_parameters` (
  `user_email` VARCHAR(200) NOT NULL,
  `parameter_id` INT(11) NOT NULL,
  `version_name` VARCHAR(40) NOT NULL,
  `bundle_id` INT(11) NOT NULL,
  `combined_network_weight` INT(11) NOT NULL,
  `product_weight` INT(11) NOT NULL,
  `time_saved` TIMESTAMP NOT NULL,
  `value` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`user_email`, `parameter_id`, `version_name`, `bundle_id`, `combined_network_weight`, `product_weight`, `time_saved`),
  INDEX `fk_user_stored_parameters_parameter1_idx` (`parameter_id` ASC),
  INDEX `fk_user_stored_parameters_product_release1_idx` (`version_name` ASC, `bundle_id` ASC, `combined_network_weight` ASC, `product_weight` ASC),
  CONSTRAINT `fk_user_stored_parameters_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `HDT_SCHEMA`.`user` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_stored_parameters_parameter1`
    FOREIGN KEY (`parameter_id`)
    REFERENCES `HDT_SCHEMA`.`parameter` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_stored_parameters_product_release1`
    FOREIGN KEY (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    REFERENCES `HDT_SCHEMA`.`product_release` (`version_name` , `bundle_id` , `combined_network_weight` , `product_weight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`sys_log` (
  `table_name` VARCHAR(30) NOT NULL,
  `action` VARCHAR(10) NULL DEFAULT NULL,
  `change_time` TIMESTAMP NULL DEFAULT NULL,
  `user_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`table_name`),
  INDEX `fk_sys_log_user1_idx` (`user_email` ASC),
  CONSTRAINT `fk_sys_log_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `HDT_SCHEMA`.`user` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`system_messages` (
  `id` INT(11) NOT NULL,
  `message` VARCHAR(1000) NULL DEFAULT NULL,
  `visible` INT(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE TABLE IF NOT EXISTS `HDT_SCHEMA`.`system_parameters` (
  `name` VARCHAR(50) NOT NULL,
  `par_type` INT(11) NOT NULL,
  `message` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`name`, `par_type`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
