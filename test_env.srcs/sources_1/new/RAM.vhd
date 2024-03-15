----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/18/2023 06:53:11 PM
-- Design Name: 
-- Module Name: RAM - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity RAM is
    Port (
    clk:in std_logic;
    we:in std_logic;
    en:in std_logic;
    adresa:in std_logic_vector(7 downto 0 );
    di:in std_logic_vector(15 downto 0);
    do:out std_logic_vector(15 downto 0));
end RAM;

architecture Behavioral of RAM is
type ram_type is array (0 to 255) of std_logic_vector(15 downto 0);
signal mem:ram_type;
begin
process(clk)
begin
if rising_edge(clk) then
if en='1' then
if we='1' then
mem(conv_integer(adresa))<=di;
else do<=mem(conv_integer(adresa));
end if;
end if;
end if;
end process;

end Behavioral;
